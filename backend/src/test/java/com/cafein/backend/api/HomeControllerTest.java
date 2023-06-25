package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cafein.backend.api.home.controller.HomeController;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.domain.cafe.service.CafeService;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

	@Mock
	private CafeService cafeService;

	@InjectMocks
	private HomeController homeController;

	@Test
	void 홈_화면에_필요한_카페_정보를_반환한다() {
		HomeResponseDTO response = HomeResponseDTO.builder()
			.cafeCount(5L)
			.cafes(Collections.emptyList())
			.build();
		given(cafeService.getHomeData(anyLong())).willReturn(response);

		final ResponseEntity<HomeResponseDTO> result = homeController.home(MEMBER_INFO_DTO);

		then(cafeService).should(times(1)).getHomeData(anyLong());
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
