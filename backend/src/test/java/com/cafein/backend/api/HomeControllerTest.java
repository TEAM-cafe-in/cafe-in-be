package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.HomeFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import com.cafein.backend.api.home.controller.HomeController;
import com.cafein.backend.api.home.dto.HomeResponseDTO;

class HomeControllerTest extends ControllerTestSupporter {

	@Test
	void 홈_화면에_필요한_카페_정보를_반환한다() throws Exception {
		HomeResponseDTO response = HomeResponseDTO.builder()
			.cafeCount(5L)
			.cafes(HOME_PROJECTION)
			.build();

		given(cafeService.getHomeData(anyLong())).willReturn(response);

		mockMvc(new HomeController(cafeService))
			.perform(get("/api/home"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeCount").value(5L))
			.andExpect(jsonPath("$.cafes[0].name").value("5to7"))
			.andExpect(jsonPath("$.cafes[0].phoneNumber").value("05012341234"));
	}
}
