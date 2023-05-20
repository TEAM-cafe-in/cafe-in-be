package com.cafein.backend.support.fixture;

import java.util.Date;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;

public class LoginFixture {

	public static final String ACCESS_TOKEN = "access_token";
	public static final String AUTHORIZATION_HEADER = "Bearer access_token";
	public static final String KAKAO_MEMBER_TYPE = "{\"memberType\":\"KAKAO\"}";
	public static final String GOOGLE_MEMBER_TYPE = "{\"memberType\":\"GOOGLE\"}";

	public static OAuthLoginDTO.Response kakaoLoginResponse() {
		return createKakaoLoginResponse();
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
}
