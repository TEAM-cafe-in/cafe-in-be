package com.cafein.backend.api.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {

	private final CafeService cafeService;
	private final ViewedCafeService viewedCafeService;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public MyPageDTO getMyPageDTO(Long memberId) {
		final List<Long> viewedCafeIds = viewedCafeService.findViewedCafes(memberId);
		log.debug("memberId = {}" , memberId);
		log.debug("viewedCafeIds = {}" , viewedCafeIds);
		return MyPageDTO.of(cafeService.findCafeInfoViewedByMember(viewedCafeIds),
			reviewRepository.findReviewsByMemberId(memberId),
			reviewRepository.countReviewByMemberId(memberId));
	}
}
