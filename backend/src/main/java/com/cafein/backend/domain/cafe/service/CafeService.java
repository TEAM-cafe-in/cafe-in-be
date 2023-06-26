package com.cafein.backend.domain.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.api.member.dto.CafeInfoViewedByMemberProjection;
import com.cafein.backend.domain.cafe.repository.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	@Transactional(readOnly = true)
	public HomeResponseDTO getHomeData(Long memberId) {
		return HomeResponseDTO.builder()
			.cafeCount(cafeRepository.count())
			.cafes(cafeRepository.getHomeData(memberId))
			.build();
	}

	@Transactional(readOnly = true)
	public CafeInfoDTO findCafeInfoById(Long memberId, Long cafeId) {
		return CafeInfoDTO.builder()
			.cafeInfoProjection(cafeRepository.findCafeInfoById(memberId, cafeId))
			.comments(cafeRepository.findCommentsByCafeId(cafeId))
			.build();
	}

	@Transactional(readOnly = true)
	public List<CafeInfoViewedByMemberProjection> findCafeInfoViewedByMember(final List<Long> viewedCafeIds) {
		return viewedCafeIds.stream()
			.map(cafeRepository::findCafeInfoViewedByMember)
			.collect(Collectors.toList());
	}
}
