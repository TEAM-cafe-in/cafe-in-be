package com.cafein.backend.integration;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.support.utils.IntegrationTest;

@IntegrationTest
class LoginIntegrationTest extends IntegrationSupporter {

	@Test
	void 카카오_토큰으로_로그인을_진행한다() {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.KAKAO))).willReturn(LOGIN_RESPONSE);

		final var response = post("/api/oauth/login", generateAccessHeader(), KAKAO_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("grantType")).isEqualTo("Bearer"),
			() -> assertThat(response.jsonPath().getString("accessToken")).isEqualTo("access_token")
		);
	}

	@Test
	void 구글_토큰으로_로그인을_진행한다() {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.GOOGLE))).willReturn(LOGIN_RESPONSE);

		final var response = post("/api/oauth/login", generateAccessHeader(), GOOGLE_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.jsonPath().getString("grantType")).isEqualTo("Bearer"),
			() -> assertThat(response.jsonPath().getString("accessToken")).isEqualTo("access_token")
		);
	}

	@Test
	void refresh_token을_이용해_access_token을_재발급한다() {
		final var response = post("/api/access-token/issue", generateRefreshHeader(member.getRefreshToken()), KAKAO_MEMBER_TYPE);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime accessTokenExpireTime = LocalDateTime.parse("2023-07-25 14:38:18", formatter);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(accessTokenExpireTime).isBefore(LocalDateTime.now())
		);
	}

	@Test
	void 로그아웃을_진행한다() {
		final var response = post("/api/logout", generateAccessHeader(access_token), KAKAO_MEMBER_TYPE);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.body().asString()).isEqualTo("logout success")
		);
	}
}
