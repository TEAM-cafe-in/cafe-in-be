package com.cafein.backend.web.googletoken.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cafein.backend.web.googletoken.dto.GoogleTokenDTO;

@FeignClient(url = "https://oauth2.googleapis.com", name = "googleTokenClient")
public interface GoogleTokenClient {

	@PostMapping(value = "/token", consumes = "application/json")
	GoogleTokenDTO.Response requestGoogleToken(@RequestHeader("Content-Type") String contentType,
		@SpringQueryMap GoogleTokenDTO.Request request);
}
