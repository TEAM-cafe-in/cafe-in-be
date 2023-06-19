package com.cafein.backend.domain.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.cafe.dto.CafeInfoProjection;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

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
	public List<Cafe> findAllByLocal(Local local) {
		return cafeRepository.findAllByLocal(local);
	}

	@Transactional(readOnly = true)
	public Integer countByLocal(Local local) {
		// return cafeRepository.countByLocal(local);
		return 0;
	}
}
