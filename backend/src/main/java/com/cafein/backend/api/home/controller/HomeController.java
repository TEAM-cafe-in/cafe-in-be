package com.cafein.backend.api.home.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.home.dto.CafeDTO;
import com.cafein.backend.api.home.dto.HomeDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

	private static final int PAGE_SIZE = 5;

	private final CafeService cafeService;
	private final MemberService memberService;

	@GetMapping("/home")
	public ResponseEntity<HomeDTO.Response> home(@RequestBody HomeDTO.Request homeRequestDTO) {
		Member findMember = memberService.getMemberByEmail(homeRequestDTO.getMemberEmail());

		Pageable pageable = PageRequest.of(
			Integer.parseInt(homeRequestDTO.getPageNo()),
			PAGE_SIZE,
			Sort.by(homeRequestDTO.getSortBy()).descending()
		);

		List<CafeDTO> cafes = cafeService.findAll(pageable)
			.stream()
			.map(CafeDTO::of)
			.collect(Collectors.toList());

		HomeDTO.Response homeResponseDTO = HomeDTO.Response.builder()
			.coffeeBean(findMember.getCoffeeBean())
			.cafes(cafes)
			.build();

		return ResponseEntity.ok(homeResponseDTO);
	}
}
