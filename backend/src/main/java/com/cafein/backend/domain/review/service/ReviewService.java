package com.cafein.backend.domain.review.service;

import static com.cafein.backend.api.review.dto.ReviewDTO.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.review.constant.CafeCongestion;
import com.cafein.backend.domain.review.entity.Review;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final CafeService cafeService;

	public ReviewResponse createReview(final ReviewRequest reviewRequestDTO, final Long cafeId, final Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		Review review = reviewRepository.save(createReviewFromDTO(reviewRequestDTO, cafeId, member));
		member.addCoffeeBean(member.getCoffeeBean());
		return ReviewResponse.builder()
				.reviewId(review.getReviewId())
				.coffeeBean(member.getCoffeeBean())
				.build();
	}

	private Review createReviewFromDTO(final ReviewRequest reviewRequestDTO, final Long cafeId, final Member member) {
		return Review.builder()
			.cafe(cafeService.findCafeByCafeId(cafeId))
			.cafeCongestion(CafeCongestion.from(reviewRequestDTO.getCafeCongestion()))
			.isClean(Boolean.parseBoolean(reviewRequestDTO.getIsClean()))
			.hasPlug(Boolean.parseBoolean(reviewRequestDTO.getHasPlug()))
			.member(member)
			.build();
	}

	@Transactional(readOnly = true)
	public void validateReview(final Long cafeId, final Long memberId) {
		final List<Long> cafeIdsOfRecentReviews = reviewRepository.findCafeIdsOfRecentReviews(memberId);
		log.info("cafeIdsOfRecentReviews = {}", cafeIdsOfRecentReviews);
		if (cafeIdsOfRecentReviews.contains(cafeId)) {
			throw new BusinessException(ErrorCode.REVIEWED_CAFE_WITHIN_A_DAY);
		}
	}
}
