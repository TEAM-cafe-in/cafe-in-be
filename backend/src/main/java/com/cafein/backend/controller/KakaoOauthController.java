package com.cafein.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.service.KakaoOathService;
import com.cafein.backend.service.dto.LoginResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoOauthController {

	private final KakaoOathService kakaoOathService;

	public ResponseEntity<LoginResponseDTO> login(@PathVariable String provider, @RequestParam String code) {
		final LoginResponseDTO loginResponseDto = kakaoOathService.login(provider, code);
		return ResponseEntity.ok().body(loginResponseDto);
	}
}
