package com.cafein.backend.domain.cafe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	@Transactional(readOnly = true)
	public List<Cafe> findAllByLocal(Local local) {
		return cafeRepository.findAllByLocal(local);
	}
}
