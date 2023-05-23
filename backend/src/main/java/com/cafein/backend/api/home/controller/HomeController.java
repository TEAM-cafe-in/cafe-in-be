package com.cafein.backend.api.home.controller;

import java.util.ArrayList;
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
import com.cafein.backend.domain.comment.service.CommentService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.viewedcafe.ViewedCafeService;
import com.cafein.backend.domain.viewedcafe.entity.ViewedCafe;
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
	private final CommentService commentService;
	private final ViewedCafeService viewedCafeService;

	@GetMapping("/home")
	public ResponseEntity<HomeDTO.Response> home(final @Valid @RequestBody HomeDTO.Request homeRequestDTO,
												 final @MemberInfo MemberInfoDTO memberInfoDTO) {
		final Member findMember = memberService.findMemberByMemberId(memberInfoDTO.getMemberId());

		List<ViewedCafe> viewedCafes = viewedCafeService.findAllByMemberId(memberInfoDTO.getMemberId());
		List<String> viewedCafesName = new ArrayList<>();
		for (ViewedCafe viewedCafe : viewedCafes) {
			viewedCafesName.add(cafeService.findById(viewedCafe.getCafeId()).getName());
		}

		final List<CafeDTO> cafes = cafeService.findAllByLocal(Local.from(homeRequestDTO.getLocal()))
			.stream()
			.map(cafe -> CafeDTO.of(cafe, commentService.countByCafeId(cafe.getCafeId())))
			.collect(Collectors.toList());

		HomeDTO.Response homeResponseDTO = HomeDTO.Response.builder()
			.coffeeBean(findMember.getCoffeeBean())
			.countCafe(cafeService.countByLocal(Local.from(homeRequestDTO.getLocal())))
			.cafes(cafes)
			.viewedCafesName(viewedCafesName)
			.build();

		return ResponseEntity.ok(homeResponseDTO);
	}
}
