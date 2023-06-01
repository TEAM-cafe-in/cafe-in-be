package com.cafein.backend.api.home.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.home.dto.HomeDTO;
import com.cafein.backend.domain.cafe.repository.CafeRepositoryImpl;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

	private final CafeService cafeService;
	private final CafeRepositoryImpl cafeRepository;

	@GetMapping("/home")
	public ResponseEntity<HomeDTO.Response> home(final @Valid @RequestBody HomeDTO.Request homeRequestDTO,
												 final @MemberInfo MemberInfoDTO memberInfoDTO) {
		final List<HomeDTO.Response> homeDTO = cafeRepository.findHomeDTO();
		System.out.println(homeDTO);
		return ResponseEntity.ok().build();
	}
}
