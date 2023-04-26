package com.cafein.backend.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cafein.backend.global.error.exception.AuthenticationException;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.jwt.constant.TokenType;
import com.cafein.backend.global.jwt.service.TokenManager;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

	private final TokenManager tokenManager;

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
		final Object handler) throws Exception {

		// Authorization Header 검증
		String authorizationHeader = request.getHeader("Authorization");
		AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

		// 토큰 검증
		String token = authorizationHeader.split(" ")[1];
		tokenManager.validateToken(token);

		// 토큰 타입 검증
		Claims tokenClaims = tokenManager.getTokenClaims(token);
		String tokenType = tokenClaims.getSubject();
		if (!TokenType.isAccessToken(tokenType)) {
			throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
		}

		return true;
	}
}
