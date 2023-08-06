package com.cafein.backend.api.cafe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.cafe.dto.CafeDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "cafe", description = "카페 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CafeController {

	private final MemberService	memberService;
	private final CafeService cafeService;
	private final ViewedCafeService viewedCafeService;

	@Tag(name = "cafe")
	@Operation(summary = "카페 상세보기 API(커피콩을 사용해서 이미 조회한 카페)", description = "카페 정보를 조회하는 API")
	@ApiResponses({
		@ApiResponse(responseCode = "C-001", description = "해당 카페는 존재하지 않습니다.")
	})
	@GetMapping("/cafe/{cafeId}")
	public ResponseEntity<CafeDTO> cafeInfo(@PathVariable Long cafeId,
										    @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO) {
		return ResponseEntity.ok(cafeService.findCafeInfoById(memberInfoDTO.getMemberId(), cafeId));
	}

	@Tag(name = "cafe")
	@Operation(summary = "카페 상세보기 API(커피콩을 사용한 혼잡도 조회)", description = "커피콩을 사용해서 카페 정보 열람 권한을 얻을때 사용하는 API")
	@ApiResponses({
		@ApiResponse(responseCode = "M-004", description = "커피 콩이 부족합니다"),
		@ApiResponse(responseCode = "C-001", description = "해당 카페는 존재하지 않습니다.")
	})
	@PostMapping("/cafe/{cafeId}")
	public ResponseEntity<CafeDTO> cafeCongestionCheck(@PathVariable Long cafeId,
		                                               @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO) {
		log.debug("memberId = {}", memberInfoDTO.getMemberId());
		final Long memberId = memberInfoDTO.getMemberId();
		viewedCafeService.validateCongestionRequest(memberId, cafeId);
		memberService.subtractCoffeeBean(memberId);
		viewedCafeService.addViewedCafe(memberService.findMemberByMemberId(memberId), cafeId);
		return ResponseEntity.ok(cafeService.findCafeInfoById(memberId, cafeId));
	}
}
