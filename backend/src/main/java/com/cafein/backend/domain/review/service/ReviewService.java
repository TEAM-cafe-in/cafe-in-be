package com.cafein.backend.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.review.dto.ReviewDTO;
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

	private final MemberService memberService;
	private final ReviewRepository reviewRepository;
	private final CafeService cafeService;

	public ReviewDTO.ReviewResponse createReview(final ReviewDTO.ReviewRequest reviewRequestDTO, final Long cafeId, final Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		reviewRepository.save(createReview(reviewRequestDTO, cafeId, member));
		member.addCoffeeBean(member.getCoffeeBean());
		return ReviewDTO.ReviewResponse.builder()
				.coffeeBean(member.getCoffeeBean())
				.build();
	}

	private Review createReview(final ReviewDTO.ReviewRequest reviewRequestDTO, final Long cafeId, final Member member) {
		return Review.builder()
			.cafe(cafeService.findCafeByCafeId(cafeId))
			.cafeCongestion(CafeCongestion.valueOf(reviewRequestDTO.getCafeCongestion()))
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
