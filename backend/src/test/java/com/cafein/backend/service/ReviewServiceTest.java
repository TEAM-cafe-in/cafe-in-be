package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.review.entity.Review;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.domain.review.service.ReviewService;
import com.cafein.backend.global.error.exception.BusinessException;
import com.cafein.backend.support.fixture.MemberFixture;
import com.cafein.backend.support.fixture.ReviewFixture;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private MemberService memberService;

	@Mock
	private CafeService cafeService;

	@Mock
	private ReviewRepository reviewRepository;

	@Test
	void 리뷰를_등록한다() {
		given(memberService.findMemberByMemberId(anyLong())).willReturn(MemberFixture.MEMBER);
		given(reviewRepository.save(any(Review.class))).willReturn(ReviewFixture.REVIEW);
		given(cafeService.findCafeByCafeId(anyLong())).willReturn(Cafe.builder().build());

		reviewService.createReview(ReviewFixture.REVIEW_REQUEST, 1L, 1L);

		then(reviewRepository).should(times(1)).save(any(Review.class));
	}

	@Test
	void 하루에_두_번_리뷰를_작성하면_예외가_발생한다() {
		given(reviewRepository.findCafeIdsOfRecentReviews(anyLong()))
			.willReturn(ReviewFixture.CAFE_IDS_OF_RECENT_REVIEWS);

		assertThatThrownBy(() -> reviewService.validateReview(1L, 1L))
			.isInstanceOf(BusinessException.class)
			.hasMessage("해당 카페에 대해 하루에 한번만 리뷰를 작성할 수 있습니다.");
	}
}
