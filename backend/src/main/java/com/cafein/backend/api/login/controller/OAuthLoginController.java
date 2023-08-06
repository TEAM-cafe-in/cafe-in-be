package com.cafein.backend.api.login.controller;

import static com.cafein.backend.api.login.dto.OAuthLoginDTO.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	private final OAuthLoginService oAuthLoginService;

	@Tag(name = "authentication")
	@Operation(summary = "소셜 로그인 API",
		description = "추후 서비스 배포 시 Refresh Token을 쿠키로 반환하도록 변경할 예정입니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "M-001", description = "잘못된 회원 타입 입니다. (memberType : Kakao)"),
		@ApiResponse(responseCode = "A-003", description = "Authorization Header가 빈값입니다."),
		@ApiResponse(responseCode = "A-004", description = "인증 타입이 Bearer 타입이 아닙니다."),
	})
	@PostMapping("/login")
	public ResponseEntity<OAuthLoginResponse> oauthLogin(@RequestBody OAuthLoginRequest requestDTO,
		HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		OAuthValidator.validateMemberType(requestDTO.getMemberType());

		String accessToken = authorizationHeader.split(" ")[1];
		OAuthLoginResponse jwtTokenOAuthLoginResponseDTO = oAuthLoginService
			.oauthLogin(accessToken, MemberType.from(requestDTO.getMemberType()));

		ResponseCookie responseCookie = ResponseCookie
			.from("refresh_token", jwtTokenOAuthLoginResponseDTO.getRefreshToken())
			.httpOnly(true)
			.sameSite("Strict")
			.maxAge(60 * 60 * 24 * 14)
			.build();

		httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
		return ResponseEntity.ok(jwtTokenOAuthLoginResponseDTO);
	}
}
