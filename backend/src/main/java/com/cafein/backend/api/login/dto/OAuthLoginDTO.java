package com.cafein.backend.api.login.dto;

import java.util.Date;

import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class OAuthLoginDTO {

	@Getter
	public static class OAuthLoginRequest {

		@Schema(description = "소셜 로그인 회원 타입", example = "KAKAO", required = true)
		private String memberType;
	}

	@Getter @Builder
	public static class OAuthLoginResponse {

		@Schema(description = "Grant Type", example = "Bearer", required = true)
		private String grantType;

		@Schema(description = "Access token", example = "eyJ0eXasIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2ODI0OTg4OTUsImV4cCI6MTY4MjQ5OTc5NSwibWVtYmVySWQiOjEsInJvbGUiOiJVU0VSIn0.YYl9XmJ2JnJm1S-4eCf11faRTcVJj9cGdI3TlerbEu1Wf_XCi8FcRT_62jNrrMYfyj2chYMlL7Td7OSSh1vxFA", required = true)
		private String accessToken;

		@Schema(description = "Access Token 만료 시간", example = "2023-04-26 18:03:15", required = true)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date accessTokenExpireTime;

		@Schema(description = "Refresh Token", example = "yJ0asBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJSRUZSRVNIIiwiaWF0IjoxNjgyNDk4ODk2LCJleHAiOjE2ODM3MDg0OTUsIm1lbWJlcklkIjoxfQ.rzEIFZRIUPrSMJ1B5e-jnXU0L8QoTQE6Od89-fxCaNkhRqkmxWPze8YXGGEYmdXlZJ6e5qJXvXItnZ2aotmqFA", required = true)
		private String refreshToken;

		@Schema(description = "Refresh Token 만료 시간", example = "2023-05-10 17:48:15", required = true)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date refreshTokenExpireTime;

		public static OAuthLoginResponse of(JwtTokenDTO jwtTokenDTO) {
			return OAuthLoginResponse.builder()
				.grantType(jwtTokenDTO.getGrantType())
				.accessToken(jwtTokenDTO.getAccessToken())
				.accessTokenExpireTime(jwtTokenDTO.getAccessTokenExpireTime())
				.refreshToken(jwtTokenDTO.getRefreshToken())
				.refreshTokenExpireTime(jwtTokenDTO.getRefreshTokenExpireTime())
				.build();
		}
	}
}
