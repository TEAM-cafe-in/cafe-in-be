package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.CafeFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cafein.backend.api.cafe.dto.CafeDTO;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class CafeServiceTest {

	@InjectMocks
	private CafeService cafeService;

	@Mock
	private CafeRepository cafeRepository;

	@Test
	void 카페_정보를_반환한다() {
		given(cafeRepository.findCafeInfoById(anyLong(), anyLong())).willReturn(CAFE_INFO_PROJECTION);

		CafeDTO cafeDTO = cafeService.findCafeInfoById(1L, 1L);

		then(cafeRepository).should(times(1)).findCafeInfoById(anyLong(), anyLong());
		then(cafeRepository).should(times(1)).findAllCommentByCafeId(anyLong());
		assertThat(cafeDTO.getCafeInfoProjection().getName()).isEqualTo("5to7");
	}

	@Test
	void 회원이_조회한_카페들의_정보를_반환한다() {
		given(cafeRepository.findCafeInfoViewedByMember(anyLong())).willReturn(CAFE_INFO_VIEWED_BY_MEMBER);

		cafeService.findCafeInfoViewedByMember(VIEWED_CAFE_IDS);

		then(cafeRepository).should(times(3)).findCafeInfoViewedByMember(anyLong());
	}

  	@Test
	void 홈_화면의_전체_카페_정보를_반환한다() {
		given(cafeRepository.count()).willReturn(5L);
		given(cafeRepository.getHomeData(1L)).willReturn(Collections.emptyList());

		cafeService.getHomeData(1L);

		then(cafeRepository).should(times(1)).count();
		then(cafeRepository).should(times(1)).getHomeData(anyLong());
	}
}
