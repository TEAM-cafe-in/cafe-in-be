package com.cafein.backend.web.googletoken.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafein.backend.web.googletoken.client.GoogleTokenClient;
import com.cafein.backend.web.googletoken.dto.GoogleTokenDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GoogleTokenController {

	private final GoogleTokenClient googleTokenClient;

	@Value("${google.client.id}")
	private String clientId;

	@Value("${google.client.secret}")
	private String clientSecret;

	@GetMapping("/google/login")
	public String login() {
		return "googleLoginForm";
	}

	@GetMapping("/oauth/google/callback")
	public @ResponseBody String loginCallback(String code) {
		String contentType = "application/x-www-form-urlencoded";
		GoogleTokenDTO.Request googleTokenRequestDto = GoogleTokenDTO.Request.builder()
			.code(code)
			.client_id(clientId)
			.client_secret(clientSecret)
			.grant_type("authorization_code")
			.redirect_uri("http://localhost:8080/oauth/google/callback")
			.build();

		GoogleTokenDTO.Response googleToken = googleTokenClient.requestGoogleToken(contentType, googleTokenRequestDto);
		return "google token : " + googleToken;
	}
}
