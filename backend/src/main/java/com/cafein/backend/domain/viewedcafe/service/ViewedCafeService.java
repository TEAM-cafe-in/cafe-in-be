package com.cafein.backend.domain.viewedcafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.viewedcafe.entity.ViewedCafe;
import com.cafein.backend.domain.viewedcafe.repository.ViewedCafeRepository;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewedCafeService {

	private final ViewedCafeRepository viewedCafeRepository;

	@Transactional(readOnly = true)
	public List<Long> findViewedCafes(Long memberId) {
		return viewedCafeRepository.findViewedCafes(memberId);
	}

	@Transactional
	public void addViewedCafe(Member member, Long cafeId) {
		viewedCafeRepository.save(
			ViewedCafe.builder()
				.cafeId(cafeId)
				.member(member)
				.build()
		);
	}

	public void validateCongestionRequest(Long memberId, Long cafeId) {
		if (viewedCafeRepository.validateCongestionRequest(memberId, cafeId) == 1) {
			throw new BusinessException(ErrorCode.CONGESTION_ALREADY_REQUESTED);
		}
	}
}
