package com.cafein.backend.service;

import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class CafeServiceTest {

	@Autowired
	private CafeService cafeService;

	@MockBean
	private CafeRepository cafeRepository;

	@Test
	void 홈_화면의_전체_카페_정보를_반환한다() {
		given(cafeRepository.count()).willReturn(5L);
		given(cafeRepository.getHomeData(1L)).willReturn(Collections.emptyList());

		cafeService.getHomeData(1L);

		then(cafeRepository).should(times(1)).count();
		then(cafeRepository).should(times(1)).getHomeData(anyLong());
	}
}
