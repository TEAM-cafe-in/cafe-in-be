package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.api.member.service.MyPageService;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;
import com.cafein.backend.support.fixture.CafeFixture;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MyPageServiceTest {

	@InjectMocks
	private MyPageService myPageService;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private ViewedCafeService viewedCafeService;

	@Mock
	private CafeService cafeService;

	@Test
	void 회원이_최근_본_매장과_작성한_게시물_정보를_반환한다() {
		given(cafeService.findCafeInfoViewedByMember(any())).willReturn(Collections.emptyList());
		given(reviewRepository.findReviewsByMemberId(anyLong())).willReturn(Collections.emptyList());
		given(reviewRepository.countReviewByMemberId(anyLong())).willReturn(5L);
		given(viewedCafeService.findViewedCafes(anyLong())).willReturn(CafeFixture.VIEWED_CAFE_IDS);

		final MyPageDTO myPageDTO = myPageService.getMyPageDTO(1L);

		assertThat(myPageDTO.getReviewCount()).isEqualTo(5L);
	}
}
