package com.cafein.backend.api.home.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.home.dto.CafeDTO;
import com.cafein.backend.api.home.dto.HomeDTO;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

	private final CafeService cafeService;
	private final MemberService memberService;

	@GetMapping("/home")
	public ResponseEntity<HomeDTO.Response> home(final @Valid @RequestBody HomeDTO.Request homeRequestDTO,
												 @MemberInfo MemberInfoDTO memberInfoDTO) {
		final Member findMember = memberService.findMemberByMemberId(memberInfoDTO.getMemberId());

		final List<CafeDTO> cafes = cafeService.findAllByLocal(Local.from(homeRequestDTO.getLocal()))
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
