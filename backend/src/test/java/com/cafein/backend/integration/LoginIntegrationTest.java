package com.cafein.backend.integration;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.cafein.backend.api.login.service.OAuthLoginService;
import com.cafein.backend.api.logout.service.LogoutService;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class LoginIntegrationTest extends IntegrationSupporter {

	@MockBean
	private OAuthLoginService oAuthLoginService;

	@MockBean
	private TokenService tokenService;

	@MockBean
	private LogoutService logoutService;

	@Test
	void 카카오_토큰으로_로그인을_진행한다() {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.KAKAO))).willReturn(LOGIN_RESPONSE);

		final ExtractableResponse<Response> response = post("/api/oauth/login", generateAccessHeader(), KAKAO_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("grantType")).isEqualTo("Bearer"),
			() -> assertThat(response.jsonPath().getString("accessToken")).isEqualTo("access_token")
		);
	}

	@Test
	void 구글_토큰으로_로그인을_진행한다() {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.GOOGLE))).willReturn(LOGIN_RESPONSE);

		final ExtractableResponse<Response> response = post("/api/oauth/login", generateAccessHeader(), GOOGLE_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("grantType")).isEqualTo("Bearer"),
			() -> assertThat(response.jsonPath().getString("accessToken")).isEqualTo("access_token")
		);
	}

	@Test
	void refresh_token을_이용해_access_token을_재발급한다() {
		given(tokenService.createAccessTokenByRefreshToken(REFRESH_TOKEN)).willReturn(ACCESS_TOKEN_RESPONSE);

		final ExtractableResponse<Response> response = post("/api/access-token/issue", generateRefreshHeader(), KAKAO_MEMBER_TYPE);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime accessTokenExpireTime = LocalDateTime.parse("2023-07-25 14:38:18", formatter);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(response.jsonPath().getString("accessToken")).isEqualTo("access_token"),
			() -> assertThat(accessTokenExpireTime).isBefore(LocalDateTime.now())
		);
	}

	@Test
	void 로그아웃을_진행한다() {
		final ExtractableResponse<Response> response = post("/api/logout", generateAccessHeader(), KAKAO_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.body().asString()).isEqualTo("logout success")
		);
	}
}
