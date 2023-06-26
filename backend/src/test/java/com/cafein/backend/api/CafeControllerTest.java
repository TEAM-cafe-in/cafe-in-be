package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.CafeFixture.*;
import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class CafeControllerTest extends ControllerTestSupporter {

	@Test
	void 카페_정보를_조회한다() throws Exception {
		given(cafeService.findCafeInfoById(anyLong(), anyLong()))
			.willReturn(CAFE_INFO_DTO);

		mockMvc.perform(get("/api/cafe/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeInfoProjection.name").value("5to7"));
	}

	@Test
	void 커피콩을_사용해서_카페_정보를_열람한다() throws Exception {
		given(cafeService.findCafeInfoById(anyLong(), anyLong())).willReturn(CAFE_INFO_DTO);

		mockMvc.perform(post("/api/cafe/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeInfoProjection.name").value("5to7"));

		then(memberService).should(times(1)).updateCoffeeBean(anyLong());
		then(viewedCafeService).should(times(1)).addViewedCafe(any(), anyLong());
	}
}
