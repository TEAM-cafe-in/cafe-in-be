package com.cafein.backend.api.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "member", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberInfoController {

	private final MemberInfoService memberInfoService;

	@Tag(name = "member")
	@Operation(summary = "회원 정보 조회 API", description = "회원 정보 조회 API")
	@ApiResponses({
		@ApiResponse(responseCode = "A-001", description = "토큰이 만료되었습니다."),
		@ApiResponse(responseCode = "A-002", description = "해당 토큰은 유효한 토큰이 아닙니다."),
		@ApiResponse(responseCode = "M-003", description = "해당 회원은 존재하지 않습니다.")
	})
	@GetMapping("/info")
	public ResponseEntity<MemberInfoResponseDTO> getMemberInfo(@MemberInfo MemberInfoDTO memberInfoDTO) {
		Long memberId = memberInfoDTO.getMemberId();
		MemberInfoResponseDTO memberInfoResponseDTO = memberInfoService.getMemberInfo(memberId);
		return ResponseEntity.ok(memberInfoResponseDTO);
	}
}
