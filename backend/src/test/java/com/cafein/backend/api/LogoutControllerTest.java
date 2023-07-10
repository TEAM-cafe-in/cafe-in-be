package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.logout.controller.LogoutController;
import com.cafein.backend.api.logout.service.LogoutService;

public class LogoutControllerTest extends ControllerTestSupporter{

	@Mock
	private LogoutService logoutService;

	@Test
	void 로그아웃을_진행한다() throws Exception {
		mockMvc(new LogoutController(logoutService))
			.perform(post("/api/logout")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value("logout success"));

		then(logoutService).should(times(1)).logout(eq(ACCESS_TOKEN));
	}
}
