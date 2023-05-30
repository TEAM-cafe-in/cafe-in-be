package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.junit.jupiter.api.DisplayNameGenerator.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafein.backend.api.home.service.HomeService;
import com.cafein.backend.api.login.service.OAuthLoginService;
import com.cafein.backend.api.login.validator.OAuthValidator;
import com.cafein.backend.api.logout.service.LogoutService;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.external.oauth.google.service.GoogleLoginApiServiceImpl;
import com.cafein.backend.external.oauth.kakao.service.KakaoLoginApiServiceImpl;
import com.cafein.backend.external.oauth.service.SocialLoginApiServiceFactory;
import com.cafein.backend.global.interceptor.AuthenticationInterceptor;
import com.cafein.backend.global.jwt.service.TokenManager;
import com.cafein.backend.global.resolver.MemberInfoArgumentResolver;
import com.cafein.backend.web.googletoken.client.GoogleTokenClient;
import com.cafein.backend.web.kakaotoken.client.KakaoTokenClient;

@WebMvcTest
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ControllerTestSupporter {

	protected MockMvc mockMvc;

	@BeforeEach
	void setUp(final WebApplicationContext context) throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.alwaysDo(MockMvcResultHandlers.print())
			.build();

		// 테스트를 위해 인터셉터 bypass
		given(authenticationInterceptor.preHandle(any(), any(), any()))
			.willReturn(true);

		// MemberInfoArgumentResolver 테스트를 위한 설정
		given(memberInfoArgumentResolver.supportsParameter(any()))
			.willReturn(true);

		given(memberInfoArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.willReturn(MEMBER_INFO_DTO);
	}

	@MockBean
	protected OAuthLoginService oAuthLoginService;

	@MockBean
	protected OAuthValidator oAuthValidator;

	@MockBean
	protected MemberService memberService;

	@MockBean
	protected MemberInfoService memberInfoService;

	@MockBean
	protected TokenManager tokenManager;

	@MockBean
	protected LogoutService logoutService;

	@MockBean
	protected TokenService tokenService;

	@MockBean
	protected CafeService cafeService;

	@MockBean
	protected HomeService homeService;

	@MockBean
	protected GoogleLoginApiServiceImpl googleLoginApiServiceImpl;

	@MockBean
	protected KakaoLoginApiServiceImpl kakaoLoginApiServiceImpl;

	@MockBean
	protected AuthenticationInterceptor authenticationInterceptor;

	@MockBean
	protected SocialLoginApiServiceFactory socialLoginApiServiceFactory;

	@MockBean
	protected MemberInfoArgumentResolver memberInfoArgumentResolver;

	@MockBean
	protected GoogleTokenClient googleTokenClient;

	@MockBean
	protected KakaoTokenClient kakaoTokenClient;
}
