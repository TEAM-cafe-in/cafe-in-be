package com.cafein.backend.domain.viewedcafe;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.viewedcafe.entity.ViewedCafe;
import com.cafein.backend.domain.viewedcafe.repository.ViewedCafeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ViewedCafeService {

	private final ViewedCafeRepository viewedCafeRepository;

	@Transactional(readOnly = true)
	public List<ViewedCafe> findAllByMemberId(Long memberId) {
		return viewedCafeRepository.findAllByMemberId(memberId);
	}
}
