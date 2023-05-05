package com.cafein.backend.global.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

	private final TokenManager tokenManager;

	/**
	 * 주어진 메소드의 파라미터가 이 ArgumentResolver 에서 지원하는 타입인지 검사한다
	 * @return true 지원하는 타입 false 지원하지 않는 타입
	 */
	@Override
	public boolean supportsParameter(final MethodParameter parameter) {
		return parameter.getParameterType().equals(MemberInfoDTO.class)
			&& parameter.hasParameterAnnotation(MemberInfo.class);
	}

	/**
	 * 이 메소드의 반환값이 대상이 되는 메소드의 파라미터에 바인딩된다.
	 * @return MemberInfoDTO
	 */
	@Override
	public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
		final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String authorizationHeader = request.getHeader("Authorization");
		String accessToken = authorizationHeader.split(" ")[1];

		Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
		Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
		String role = (String) tokenClaims.get("role");

		return MemberInfoDTO.builder()
			.memberId(memberId)
			.role(Role.from(role))
			.build();
	}
}
