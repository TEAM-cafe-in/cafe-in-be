package com.cafein.backend.api.logout.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.logout.service.LogoutService;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "authentication", description = "로그인/로그아웃/토큰 재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutController {

	private final LogoutService logoutService;

	@Tag(name = "authentication")
	@Operation(summary = "로그아웃 API", description = "로그아웃시 Refresh Token 만료 처리")
	@ApiResponses({
		@ApiResponse(responseCode = "A-003", description = "Authorization Header가 빈값입니다."),
		@ApiResponse(responseCode = "A-004", description = "인증 타입이 Bearer 타입이 아닙니다."),
	})
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

		String accessToken = authorizationHeader.split(" ")[1];
		logoutService.logout(accessToken);

		return ResponseEntity.ok("logout success");
	}
}
