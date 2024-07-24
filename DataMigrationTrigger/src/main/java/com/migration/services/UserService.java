package com.migration.services;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.migration.enums.Gender;
import com.migration.jwt.response.ResponseBody;
import com.migration.mariadb.user.CommonUserRepository;
import com.migration.mariadb.user.EgovAdminUserRepository;
import com.migration.mariadb.user.EgovCommonUserRepository;
import com.migration.mariadb.user.EgovSuperUserRepository;
import com.migration.mariadb.user.dto.UserDto;
import com.migration.utils.AesUtil;
import com.migration.utils.JsonUtil;
import com.privacy.pCrypto;
import com.privacy.pCryptoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	EgovCommonUserRepository egovCommonUserRepo;
	@Autowired
	EgovAdminUserRepository egovAdminUserRepo;
	@Autowired
	EgovSuperUserRepository egovSuperUserRepo;
	@Autowired
	CommonUserRepository commonUserRepo;
	@Autowired
	JsonUtil jsonUtil;
	@Autowired
	AesUtil aesUtil;

	private final boolean encryptYn = true;

	public List<UserDto> findAllUsers() {
		List<UserDto> userList = null;

		try {
			// 일반 사용자 목록
			userList = egovCommonUserRepo.findAllUserDto();
			// 시설 관리자 목록
			userList.addAll(egovAdminUserRepo.findAllUserDto());

			// apply all users
			userList.stream().forEach(u -> {
				// convert gender code to korean
				Gender gender = Gender.enumOf(u.getGender());
				u.setGender(gender == null ? null : gender.getName());

				// formmatting birthday
				String birth = u.getBirth();
				if (birth != null) {
					String formattedBirth = String.join("", birth.split("-"));
					u.setBirth(formattedBirth);
				}

				// decrypt phone number
				String phone = u.getPhone();
				if (encryptYn && phone != null && phone.startsWith("^")) {
					try {
						String decryptedPhone = pCrypto.Decrypt("normal", phone, "", 0);
						u.setPhone(decryptedPhone);
					} catch (UnsupportedEncodingException | pCryptoException e) {
						log.error(e.getMessage());
						throw new NotFoundException("Not Found");
					}
				}
			});
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new NotFoundException("Not Found");
		}

		return userList;
	}

	public ResponseBody getUsers() {
		String encryptedData = null;

		try {
			String jsonData = jsonUtil.toJson(findAllUsers());
			encryptedData = encryptYn ? aesUtil.encrypt(jsonData) : jsonData;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new NotFoundException("Not Found");
		}

    log.info("Response success: getUsers");
		return ResponseBody.builder().status(200).data(encryptedData).build();
	}

	public ResponseBody getUser(String userId) {
		String encryptedData = null;

		try {
			// 일반 사용자 조회
			UserDto user = egovCommonUserRepo.findByUserId(userId);
			// 결과가 없는 경우 시설 관리자 조회
			user = user != null ? user : egovAdminUserRepo.findByUserId(userId);
			if (user != null) {
				// convert gender code to korean
				Gender gender = Gender.enumOf(user.getGender());
				user.setGender(gender == null ? null : gender.getName());

				// formmatting birthday
				String birth = user.getBirth();
				if (birth != null) {
					String formattedBirth = String.join("", birth.split("-"));
					user.setBirth(formattedBirth);
				}

				// decrypt phone number
				String phone = user.getPhone();
				if (phone != null && phone.startsWith("^")) {
					try {
						String decryptedPhone = pCrypto.Decrypt("normal", phone, "", 0);
						user.setPhone(decryptedPhone);
					} catch (UnsupportedEncodingException | pCryptoException e) {
						log.error(e.getMessage());
						throw new NotFoundException("Not Found");
					}
				}
			}

			String jsonData = jsonUtil.toJson(user);
			if (user == null)
				encryptedData = null;
			else if (encryptYn)
				encryptedData = aesUtil.encrypt(jsonData);
			else
				encryptedData = jsonData;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new NotFoundException("Not Found");
		}

    log.info("Response success: getUser({})", userId);
		return ResponseBody.builder().status(200).data(encryptedData).build();
	}
}
