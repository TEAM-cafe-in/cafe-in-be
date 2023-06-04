package com.cafein.backend.api.home.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.home.dto.HomeDTO;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

	private final CafeService cafeService;

	@GetMapping("/home")
	public ResponseEntity<List<HomeResponseDTO>> home(final @Valid @RequestBody HomeDTO.Request homeRequestDTO,
												 HttpServletRequest httpServletRequest) {
		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		return ResponseEntity.ok(List.copyOf(cafeService.getHomeDTO(homeRequestDTO.getLocal())));
	}
}
