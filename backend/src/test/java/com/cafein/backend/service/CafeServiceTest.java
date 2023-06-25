package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.CafeFixture.*;
import static org.mockito.BDDMockito.*;

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
	void 카페_정보를_반환하다() {
		given(cafeRepository.findCafeInfoById(anyLong(), anyLong())).willReturn(CAFE_INFO_PROJECTION);
		given(cafeRepository.findCommentsByCafeId(anyLong())).willReturn(CAFE_COMMENTS);

		cafeService.findCafeInfoById(1L, 1L);

		then(cafeRepository).should(times(1)).findCafeInfoById(anyLong(), anyLong());
		then(cafeRepository).should(times(1)).findCommentsByCafeId(anyLong());
	}

	@Test
	void 회원이_조회한_카페들의_정보를_반환한다() {
		given(cafeRepository.findCafeInfoViewedByMember(anyLong())).willReturn(CAFE_INFO_VIEWED_BY_MEMBER);

		cafeService.findCafeInfoViewedByMember(VIEWED_CAFE_IDS);

		then(cafeRepository).should(times(3)).findCafeInfoViewedByMember(anyLong());
	}
}
