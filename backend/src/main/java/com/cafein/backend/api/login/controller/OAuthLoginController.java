package com.cafein.backend.api.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

	private final OAuthLoginService oAuthLoginService;

	@Tag(name = "authentication")
	@Operation(summary = "소셜 로그인 API",
		description = "refresh token 예시 쿠키 값입니다." +
			"Set-Cookie: refresh_token= eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJSRUZSRVNIIiwiaWF0IjoxNjg0NDc3NDkyLCJleHAiOjE2ODU2ODcwOTIsIm1lbWJlcklkIjoxfQ.VoaLQEd4QkQ5Q7pzrkQbfRVdYcX2flmdP4fELrn5J2_4T0lfuhcrB1r5Ma5F7YyqKzyMGR-Ol30wMS_VDsf0mg " +
			"HttpOnly; SameSite=Strict; Max-Age=60 * 60 * 24 * 14; Expires=Thu, 31 Mar 2023 14:24:52 GMT;")
	@ApiResponses({
		@ApiResponse(responseCode = "M-001", description = "잘못된 회원 타입 입니다. (memberType : Kakao"),
		@ApiResponse(responseCode = "A-003", description = "Authorization Header가 빈값입니다."),
		@ApiResponse(responseCode = "A-004", description = "인증 타입이 Bearer 타입이 아닙니다."),
	})
	@PostMapping("/login")
	public ResponseEntity<OAuthLoginDTO.Response> oauthLogin(@RequestBody OAuthLoginDTO.Request oauthLoginRequestDTO,
		HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
		OAuthValidator.validateMemberType(oauthLoginRequestDTO.getMemberType());

		String accessToken = authorizationHeader.split(" ")[1];
		OAuthLoginDTO.Response jwtTokenResponseDTO = oAuthLoginService
			.oauthLogin(accessToken, MemberType.from(oauthLoginRequestDTO.getMemberType()));

		ResponseCookie responseCookie = ResponseCookie
			.from("refresh_token", jwtTokenResponseDTO.getRefreshToken())
			.httpOnly(true)
			.sameSite("Strict")
			.maxAge(60 * 60 * 24 * 14)		//2주
			.build();

		httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
		return ResponseEntity.ok(jwtTokenResponseDTO);
	}
}
