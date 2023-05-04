package com.cafein.backend.domain.cafe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {
	//TODO CafeServiceTest 작성
	private final CafeRepository cafeRepository;

	// public Cafe registerCafe(Cafe cafe) {
	// 	return cafeRepository.save(cafe);
	// }
	//
	// @Transactional(readOnly = true)
	// public Cafe findOne(Long cafeId) {
	// 	return cafeRepository.findById(cafeId)
	// 		.orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAFE_NOT_EXIST));
	// }
	//
	// @Transactional(readOnly = true)
	// public Page<Cafe> findCafeByLocal(Pageable pageable) {
	// 	return cafeRepository.findCafeByLocal(pageable);
	// }

	// @Transactional(readOnly = true)
	// public List<Cafe> findAll() {
	// 	return cafeRepository.findAll();
	// }

	@Transactional(readOnly = true)
	public Page<Cafe> findAll(Pageable pageable) {
		return cafeRepository.findAll(pageable);
	}
}
