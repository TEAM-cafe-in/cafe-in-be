package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.HomeFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cafein.backend.api.home.controller.HomeController;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.domain.cafe.service.CafeService;

class HomeControllerTest extends ControllerTestSupporter {

	@Mock
	private CafeService cafeService;

	@Test
	void 홈_화면에_필요한_카페_정보를_반환한다() throws Exception {
		HomeResponseDTO response = HomeResponseDTO.builder()
			.cafeCount(5L)
			.cafes(HOME_PROJECTION)
			.build();

		given(cafeService.getHomeData(any())).willReturn(response);

		mockMvc(new HomeController(cafeService))
			.perform(MockMvcRequestBuilders
				.get("/api/home"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.cafeCount").value(5L))
			.andExpect(jsonPath("$.cafes[0].name").value("5to7"));
	}
}
