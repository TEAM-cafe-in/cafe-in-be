package com.cafein.backend.support.fixture;

import java.util.Date;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;

public class LoginFixture {

	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String AUTHORIZATION_HEADER_ACCESS = "Bearer access_token";
	public static final String AUTHORIZATION_HEADER_REFRESH = "Bearer refresh_token";
	public static final String KAKAO_MEMBER_TYPE = "{\"memberType\":\"KAKAO\"}";
	public static final String GOOGLE_MEMBER_TYPE = "{\"memberType\":\"GOOGLE\"}";

	public static OAuthLoginDTO.Response kakaoLoginResponse() {
		return createKakaoLoginResponse();
	}

	public static AccessTokenResponseDTO accessTokenResponse() {
		return createAccessTokenResponse();
	}

	private static OAuthLoginDTO.Response createKakaoLoginResponse() {
		return OAuthLoginDTO.Response.builder()
			.grantType("Bearer")
			.accessToken("access_token")
			.accessTokenExpireTime(new Date(System.currentTimeMillis()))
			.refreshToken("refresh_token")
			.refreshTokenExpireTime(new Date(System.currentTimeMillis()))
			.build();
	}

	private static AccessTokenResponseDTO createAccessTokenResponse() {
		return AccessTokenResponseDTO.builder()
			.grantType("Bearer")
			.accessToken("access_token")
			.accessTokenExpireTime(new Date(System.currentTimeMillis()))
			.build();
	}
}
