package com.cafein.backend.api.login.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.api.login.service.OAuthLoginService;
import com.cafein.backend.api.login.validator.OAuthValidator;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "authentication", description = "로그인/로그아웃/토큰 재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthLoginController {

	private final OAuthValidator oAuthValidator;
	private final OAuthLoginService oAuthLoginService;

	@Tag(name = "authentication")
	@Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
	@ApiResponses({
		@ApiResponse(responseCode = "M-001", description = "잘못된 회원 타입 입니다. (memberType : Kakao"),
		@ApiResponse(responseCode = "A-003", description = "Authorization Header가 빈값입니다."),
		@ApiResponse(responseCode = "A-004", description = "인증 타입이 Bearer 타입이 아닙니다."),
	})
	@PostMapping("/login")
	public ResponseEntity<OAuthLoginDTO.Response> oauthLogin(@RequestBody OAuthLoginDTO.Request oauthLoginRequestDTO,
		HttpServletRequest httpServletRequest) {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		oAuthValidator.validateMemberType(oauthLoginRequestDTO.getMemberType());

		String accessToken = authorizationHeader.split(" ")[1];
		OAuthLoginDTO.Response jwtTokenResponseDTO = oAuthLoginService
			.oauthLogin(accessToken, MemberType.from(oauthLoginRequestDTO.getMemberType()));

		return ResponseEntity.ok(jwtTokenResponseDTO);
	}
}
