package com.cafein.backend.api.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberInfoController {

	private final MemberInfoService memberInfoService;
	private final TokenManager tokenManager;

	@GetMapping("/info")
	public ResponseEntity<MemberInfoResponseDTO> getMemberInfo(
		@RequestHeader("Authorization") String authorizationHeader) {

		String accessToken = authorizationHeader.split(" ")[1];
		Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
		Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
		MemberInfoResponseDTO memberInfoResponseDTO = memberInfoService.getMemberInfo(memberId);

		return ResponseEntity.ok(memberInfoResponseDTO);
	}
}
