package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.token.controller.TokenController;

class TokenControllerTest extends ControllerTestSupporter {

	@Test
	void refresh_token을_이용해_access_token을_재발급한다() throws Exception {
		given(tokenService.createAccessTokenByRefreshToken(REFRESH_TOKEN))
			.willReturn(ACCESS_TOKEN_RESPONSE);

		mockMvc(new TokenController(tokenService))
			.perform(post("/api/access-token/issue")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_REFRESH)
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.grantType").value("Bearer"))
			.andExpect(jsonPath("$.accessToken").value("access_token"));
	}
}
