package com.cafein.backend.api.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.api.token.service.TokenService;
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
public class TokenController {

	private final TokenService tokenService;

	@Tag(name = "authentication")
	@Operation(summary = "Access Token 재발급 API", description = "추후 서비스 배포시 RefreshToken은 쿠키를 통하여 사용하도록 변경할 예정입니다. 현재는 헤더에 RefreshToken을 보내시면 됩니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "A-001", description = "토큰이 만료되었습니다."),
		@ApiResponse(responseCode = "A-002", description = "해당 토큰은 유효한 토큰이 아닙니다."),
		@ApiResponse(responseCode = "A-003", description = "Authorization Header가 빈값입니다."),
		@ApiResponse(responseCode = "A-004", description = "인증 타입이 Bearer 타입이 아닙니다."),
		@ApiResponse(responseCode = "A-005", description = "해당 Refresh Token은 존재하지 않습니다."),
	})
	@PostMapping("/access-token/issue")
	public ResponseEntity<AccessTokenResponseDTO> createAccessToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

		String refreshToken = authorizationHeader.split(" ")[1];
		AccessTokenResponseDTO accessTokenResponseDTO = tokenService.createAccessTokenByRefreshToken(refreshToken);
		return ResponseEntity.status(HttpStatus.CREATED).body(accessTokenResponseDTO);
	}
}
