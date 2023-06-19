package com.cafein.backend.domain.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public HomeResponseDTO getHomeData() {
		return HomeResponseDTO.builder()
			.cafeCount(cafeRepository.count())
			.cafes(cafeRepository.getHomeData())
			.build();
	}

	@Transactional(readOnly = true)
	public Cafe findById(Long cafeId) {
		return cafeRepository.findById(cafeId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAFE_NOT_EXIST));
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
