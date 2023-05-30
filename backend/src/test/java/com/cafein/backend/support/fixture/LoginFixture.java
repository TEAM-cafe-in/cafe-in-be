package com.cafein.backend.support.fixture;

import java.util.Date;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.external.oauth.model.OAuthAttributes;

public class LoginFixture {

	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String AUTHORIZATION_HEADER_ACCESS = "Bearer access_token";
	public static final String AUTHORIZATION_HEADER_REFRESH = "Bearer refresh_token";
	public static final String KAKAO_MEMBER_TYPE = "{\"memberType\":\"KAKAO\"}";
	public static final String GOOGLE_MEMBER_TYPE = "{\"memberType\":\"GOOGLE\"}";

	public static final OAuthLoginDTO.Response KAKAO_LOGIN_RESPONSE = kakaoLoginResponse();
	public static final AccessTokenResponseDTO ACCESS_TOKEN_RESPONSE = accessTokenResponse();
	public static final OAuthAttributes KAKAO_OAUTH_ATTRIBUTES = createKakaoOauthAttributes();
	public static final OAuthAttributes GOOGLE_OAUTH_ATTRIBUTES = createGoogleOauthAttributes();

	private static OAuthAttributes createKakaoOauthAttributes() {
		return OAuthAttributes.builder()
			.name("chan")
			.email("test@cafein.com")
			.profile("profile")
			.memberType(MemberType.KAKAO)
			.build();
	}

	private static OAuthAttributes createGoogleOauthAttributes() {
		return OAuthAttributes.builder()
			.name("chan")
			.email("test@cafein.com")
			.profile("profile")
			.memberType(MemberType.GOOGLE)
			.build();
	}

	private static OAuthLoginDTO.Response kakaoLoginResponse() {
		return OAuthLoginDTO.Response.builder()
			.grantType("Bearer")
			.accessToken("access_token")
			.accessTokenExpireTime(new Date(System.currentTimeMillis()))
			.refreshToken("refresh_token")
			.refreshTokenExpireTime(new Date(System.currentTimeMillis()))
			.build();
	}

	private static AccessTokenResponseDTO accessTokenResponse() {
		return AccessTokenResponseDTO.builder()
			.grantType("Bearer")
			.accessToken("access_token")
			.accessTokenExpireTime(new Date(System.currentTimeMillis()))
			.build();
	}
}
