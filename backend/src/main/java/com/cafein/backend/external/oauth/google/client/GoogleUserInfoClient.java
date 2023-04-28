package com.cafein.backend.external.oauth.google.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;

import com.cafein.backend.external.oauth.google.dto.GoogleUserInfoResponseDTO;

@FeignClient(url = "https://www.googleapis.com", name = "googleUserInfoClient")
public interface GoogleUserInfoClient {

	@GetMapping(value = "/oauth2/v2/userinfo", consumes = "application/json")

	GoogleUserInfoResponseDTO getGoogleUserInfo(@RequestHeader("Authorization") String accessToken);
}
