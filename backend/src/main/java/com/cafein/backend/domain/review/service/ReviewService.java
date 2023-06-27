package com.cafein.backend.domain.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.review.dto.ReviewDTO;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
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
	private final CafeRepository cafeRepository;

	public ReviewDTO.Response createReview(final ReviewDTO.Request requestDTO, final Long cafeId, final Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		reviewRepository.save(createReview(requestDTO, cafeId, member));
		member.addCoffeeBean(member.getCoffeeBean());
		return ReviewDTO.Response.builder()
				.coffeeBean(member.getCoffeeBean())
				.build();
	}

	private Review createReview(final ReviewDTO.Request requestDTO, final Long cafeId, final Member member) {
		return Review.builder()
			.cafe(cafeRepository.findById(cafeId).orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_EXIST)))
			.cafeCongestion(CafeCongestion.valueOf(requestDTO.getCafeCongestion()))
			.isClean(Boolean.parseBoolean(requestDTO.getIsClean()))
			.hasPlug(Boolean.parseBoolean(requestDTO.getHasPlug()))
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
