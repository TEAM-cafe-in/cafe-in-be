package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.CafeFixture.*;
import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.cafe.controller.CafeController;

class CafeControllerTest extends ControllerTestSupporter {

	@Test
	void 카페_정보를_조회한다() throws Exception {
		given(cafeService.findCafeInfoById(anyLong(), eq(1L))).willReturn(CAFE_INFO_DTO);

		mockMvc(new CafeController(memberService, cafeService, reviewService, viewedCafeService))
			.perform(
				get("/api/cafe/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeInfo.name").value("5to7"));
	}

	@Test
	void 커피콩을_사용해서_카페_정보를_열람한다() throws Exception {
		given(cafeService.findCafeInfoById(anyLong(), eq(1L))).willReturn(CAFE_INFO_DTO);

		mockMvc(new CafeController(memberService, cafeService, reviewService, viewedCafeService))
			.perform(
				post("/api/cafe/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeInfo.name").value("5to7"));

		then(memberService).should(times(1)).subtractCoffeeBean(any());
		then(viewedCafeService).should(times(1)).addViewedCafe(any(), any());
	}
}
