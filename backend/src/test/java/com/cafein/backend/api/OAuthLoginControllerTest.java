package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.login.controller.OAuthLoginController;
import com.cafein.backend.domain.member.constant.MemberType;

class OAuthLoginControllerTest extends ControllerTestSupporter {

	@Test
	void 카카오_토큰으로_로그인을_진행한다() throws Exception {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.KAKAO)))
			.willReturn(LOGIN_RESPONSE);

		mockMvc(new OAuthLoginController(oAuthLoginService))
			.perform(post("/api/oauth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
				.content(KAKAO_MEMBER_TYPE)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));

		then(oAuthLoginService).should(times(1)).oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.KAKAO));
	}

	@Test
	void 구글_토큰으로_로그인을_진행한다() throws Exception {
		given(oAuthLoginService.oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.GOOGLE)))
			.willReturn(LOGIN_RESPONSE);

		mockMvc(new OAuthLoginController(oAuthLoginService))
			.perform(post("/api/oauth/login")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
				.content(GOOGLE_MEMBER_TYPE)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));

		then(oAuthLoginService).should(times(1)).oauthLogin(eq(ACCESS_TOKEN), eq(MemberType.GOOGLE));
	}
}
