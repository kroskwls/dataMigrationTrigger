package com.migration.mariadb.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "User")
public class UserDto {
	@Schema(description = "사용자아이디", example = "user-1")
	private String userId;
	
	@Schema(description = "사용자명", example = "사용자명-1")
	private String name;
	
	@Schema(description = "성별", example = "남자")
	private String gender;

	@Schema(description = "생년월일", example = "19991225")
	private String birth;
	
	@Schema(description = "전화번호", example = "010-0000-0000")
	private String phone;
	
	@Schema(description = "이메일", example = "email@domain.com")
	private String email;
}
