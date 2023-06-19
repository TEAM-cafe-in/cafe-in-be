package com.cafein.backend.api.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "home", description = "홈 화면 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

	private final CafeService cafeService;

	@Tag(name = "home")
	@Operation(summary = "홈 화면 API", description = "홈 화면에 필요한 모든 카페 정보를 반환하는 API 입니다.")
	@GetMapping("/home")
	public ResponseEntity<HomeResponseDTO> home(@ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO) {
		return ResponseEntity.ok(cafeService.getHomeData(memberInfoDTO.getMemberId()));
	}
}
