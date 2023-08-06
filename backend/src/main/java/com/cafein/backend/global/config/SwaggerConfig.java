package com.cafein.backend.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.cafein.backend.api"))	//API 패키지 경로
			.paths(PathSelectors.ant("/api/**"))
			.build()
			.apiInfo(apiInfo())			//API 문서에 대한 정보 추가
			.securityContexts(Arrays.asList(securityContext()))
			.securitySchemes(Arrays.asList(apikey()))
			.globalRequestParameters(globalParameters());
	}

	private List<RequestParameter> globalParameters() {
		return Arrays.asList(
			new RequestParameterBuilder()
				.in(ParameterType.HEADER)
				.name("Authorization")
				.description("HTTP Authorization Header")
				.required(Boolean.TRUE)
				.build()
		);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Cafein API 문서")
			.description("Cafein 애플리케이션 API 문서입니다.")
			.version("0.1")
			.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}

	private ApiKey apikey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}
}
