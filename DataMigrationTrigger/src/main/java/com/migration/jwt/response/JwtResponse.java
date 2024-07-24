package com.migration.jwt.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "AuthResponse")
public class JwtResponse {
	@Schema(description = "통신상태", example = "200")
	private int status;
	
	@Schema(description = "토큰종류", example = "Bearer")
	private final String token_type = "Bearer";

	@Schema(description = "인증토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
	private String access_token;
	
	@Schema(description = "만료시간(단위: ms)", example = "1800000")
    private int expires_in;
}
