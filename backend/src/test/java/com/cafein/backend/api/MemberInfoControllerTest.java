package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class MemberInfoControllerTest extends ControllerTestSupporter {

	@Test
	void 회원_정보를_가져온다() throws Exception {
		given(memberInfoService.getMemberInfo(any()))
			.willReturn(MEMBER_INFO_RESPONSE_DTO);

		mockMvc.perform(get("/api/member/info")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.memberId").value(1))
			.andExpect(jsonPath("$.email").value("test@test.com"));
	}
}
