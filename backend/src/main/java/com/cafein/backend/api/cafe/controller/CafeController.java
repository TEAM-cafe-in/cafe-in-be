package com.cafein.backend.api.cafe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "cafe", description = "카페 상세보기 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CafeController {

	private final MemberService	memberService;
	private final CafeService cafeService;
	private final ViewedCafeService viewedCafeService;

	@Tag(name = "cafe")
	@Operation(summary = "카페 상세보기 API", description = "카페 정보를 조회하는 API")
	@GetMapping("/cafe/{cafeId}")
	public ResponseEntity<CafeInfoDTO> cafeInfo(@PathVariable Long cafeId,
											    @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO) {
		return ResponseEntity.ok(cafeService.findCafeInfoById(memberInfoDTO.getMemberId(), cafeId));
	}

	@Tag(name = "cafe")
	@Operation(summary = "카페 상세보기 API", description = "커피콩을 사용해서 카페 정보 열람 권한을 얻을때 사용하는 API")
	@PostMapping("/cafe/{cafeId}")
	public ResponseEntity<CafeInfoDTO> cafeCongestionCheck(@PathVariable Long cafeId,
								           		           @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO) {
		log.debug("memberId = {}", memberInfoDTO.getMemberId());
		memberService.updateCoffeeBean(memberInfoDTO.getMemberId());
		viewedCafeService.addViewedCafe(memberService.findMemberByMemberId(memberInfoDTO.getMemberId()), cafeId);
		return ResponseEntity.ok(cafeService.findCafeInfoById(memberInfoDTO.getMemberId(), cafeId));
	}
}
