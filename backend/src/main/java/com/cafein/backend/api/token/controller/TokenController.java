package com.cafein.backend.api.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

	private final TokenService tokenService;

	@PostMapping("/access-token/issue")
	public ResponseEntity<AccessTokenResponseDTO> createAccessToken(HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

		String refreshToken = authorizationHeader.split(" ")[1];
		AccessTokenResponseDTO accessTokenResponseDTO = tokenService.createAccessTokenByRefreshToken(refreshToken);
		return ResponseEntity.ok(accessTokenResponseDTO);
	}
}
