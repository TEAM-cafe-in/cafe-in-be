package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.cafein.backend.domain.member.constant.MemberType;

class OAuthLoginControllerTest extends ControllerTestSupporter {

	@Test
	void 로그인화면을_호출한다() throws Exception {
		mockMvc.perform(get("/login"))
			.andExpect(status().isOk());
	}

	@Test
	void 구글_토큰으로_로그인을_진행한다() throws Exception {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.GOOGLE)))
			.willReturn(KAKAO_LOGIN_RESPONSE);

		mockMvc.perform(post("/api/oauth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
				.content(GOOGLE_MEMBER_TYPE)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));
	}

	@Test
	void 카카오_토큰으로_로그인을_진행한다() throws Exception {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.KAKAO)))
			.willReturn(KAKAO_LOGIN_RESPONSE);

		mockMvc.perform(post("/api/oauth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
				.content(KAKAO_MEMBER_TYPE)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));
	}

	@Test
	void refresh_token을_이용해_access_token을_재발급한다() throws Exception {
		given(tokenService.createAccessTokenByRefreshToken(REFRESH_TOKEN))
			.willReturn(ACCESS_TOKEN_RESPONSE);

		mockMvc.perform(post("/api/access-token/issue")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_REFRESH)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));
	}

	@Test
	void 로그아웃을_진행한다() throws Exception {
		final MvcResult result = mockMvc.perform(post("/api/logout")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andReturn();

		assertThat(result.getResponse().getContentAsString()).isEqualTo("logout success");
	}
}
