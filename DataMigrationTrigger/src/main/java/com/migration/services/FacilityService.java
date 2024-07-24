package com.migration.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.migration.jwt.response.ResponseBody;
import com.migration.mariadb.facility.FacilityRepository;
import com.migration.mariadb.facility.dto.FacilityDto;
import com.migration.mariadb.facility.dto.FacilityWithUserDto;
import com.migration.mariadb.facility.dto.UsersWithFacilityDto;
import com.migration.mariadb.user.dto.UserDto;
import com.migration.utils.AesUtil;
import com.migration.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FacilityService {
  @Autowired
  FacilityRepository facilityRepo;
  @Autowired
  UserService userService;
  @Autowired
  JsonUtil jsonUtil;
  @Autowired
  AesUtil aesUtil;

  private final boolean encryptYn = true;

  public List<FacilityWithUserDto> findAllFacilities() {
    List<FacilityWithUserDto> datas = new ArrayList<>();

    try {
      // 시설과 매핑된 사용자 아이디
      List<Map<String, String>> results = facilityRepo.findAllUsersWithFacilityDtos();
      List<UsersWithFacilityDto> userMappingList = new ArrayList<>();
      for (Map<String, String> result : results) {
        String userId = result.get("userId");
        String facilityName = result.get("facilityName");
        UsersWithFacilityDto user = UsersWithFacilityDto.builder()
            .userId(userId)
            .facilityName(facilityName)
            .build();
        userMappingList.add(user);
      }

      // 시설목록 정보
      List<FacilityDto> facilities = facilityRepo.findAllFacilityDto();

      // 사용자 정보 목록
      List<UserDto> users = userService.findAllUsers();

      // 각 시설에 포함된 유저목록
      for (FacilityDto facility : facilities) {
        // 시설에 소속된 사용자 아이디 목록
        List<UsersWithFacilityDto> filteredUsers = userMappingList.stream()
            .filter(u -> u.getFacilityName().equals(facility.getName())).collect(Collectors.toList());

        // 시설에 소속된 사용자 전체 정보 목록
        List<UserDto> userList = new ArrayList<>();
        for (UsersWithFacilityDto user : filteredUsers) {
          String userId = user.getUserId();
          UserDto dto = users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
          if (dto != null) {
            userList.add(dto);
          }
        }

        FacilityWithUserDto data = FacilityWithUserDto.builder()
            .name(facility.getName())
            .type(facility.getType())
            .users(userList)
            .build();

        datas.add(data);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new NotFoundException("Not Found");
    }

    return datas;
  }

  public ResponseBody getFacilities() {
    String encryptedData = null;

    try {
      String jsonData = jsonUtil.toJson(findAllFacilities());
      encryptedData = encryptYn ? aesUtil.encrypt(jsonData) : jsonData;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new NotFoundException("Not Found");
    }

    log.info("Response success: getFacilities");
    return ResponseBody.builder().status(200).data(encryptedData).build();
  }

  public FacilityWithUserDto findFacilityByName(String facilityName) {
    FacilityWithUserDto data = null;

    try {
      // 시설 기본 정보
      FacilityDto facility = facilityRepo.findFacilityDtoByName(facilityName);
      if (facility == null) return null;

      // 시설에 속한 사용자 ID 목록
      List<Map<String, String>> results = facilityRepo.findUsersWithFacilityDtosByFacilityName(facilityName);
      // 전체 사용자 정보 목록
      List<UserDto> users = userService.findAllUsers();
      // 시설에 속한 사용자 전체 정보 목록
      List<UserDto> userList = new ArrayList<>();
      for (Map<String, String> result : results) {
        String userId = result.get("userId");
        UserDto user = users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
        if (user != null) {
          userList.add(user);
        }
      }
      
      data = FacilityWithUserDto.builder()
        .name(facilityName)
        .type(facility.getType())
        .users(userList)
        .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new NotFoundException("Not Found");
    }

    return data;
  }

  public ResponseBody getFacility(String facilityName) {
    String encryptedData = null;

    try {
      FacilityWithUserDto facility = findFacilityByName(facilityName);

			String jsonData = jsonUtil.toJson(facility);
			if (facility == null)
				encryptedData = null;
			else if (encryptYn)
				encryptedData = aesUtil.encrypt(jsonData);
			else
				encryptedData = jsonData;
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new NotFoundException("Not Found");
    }

    log.info("Response success: getFacilityByName({})", facilityName);
    return ResponseBody.builder().status(200).data(encryptedData).build();
  }
}
