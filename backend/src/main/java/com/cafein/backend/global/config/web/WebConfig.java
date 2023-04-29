package com.cafein.backend.global.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cafein.backend.global.interceptor.AuthenticationInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOriginPatterns("*")
			.allowedMethods(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.PATCH.name(),
				HttpMethod.DELETE.name(),
				HttpMethod.OPTIONS.name()
			)
			.maxAge(3600);
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
			.order(1)
			.addPathPatterns("/api/**")
			.excludePathPatterns("/api/oauth/login",
				"/api/access-token/issue",
				"/api/logout",
				"/api/health");
	}
}
