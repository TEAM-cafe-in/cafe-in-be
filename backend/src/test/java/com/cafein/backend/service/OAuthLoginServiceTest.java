package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.api.login.service.OAuthLoginService;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.external.oauth.google.service.GoogleLoginApiServiceImpl;
import com.cafein.backend.external.oauth.kakao.service.KakaoLoginApiServiceImpl;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OAuthLoginServiceTest {

	@Autowired
	private OAuthLoginService oAuthLoginService;

	@MockBean
	private MemberService memberService;

	@MockBean
	private KakaoLoginApiServiceImpl kakaoLoginApiServiceImpl;

	@MockBean
	private GoogleLoginApiServiceImpl googleLoginApiServiceImpl;

	@Test
	void 신규로_로그인을하면_회원을_등록하고_토큰을_생성한다() {
		given(kakaoLoginApiServiceImpl.getUserInfo(ACCESS_TOKEN)).willReturn(KAKAO_OAUTH_ATTRIBUTES);
		given(memberService.findMemberByEmail(anyString())).willReturn(Optional.empty());
		given(memberService.registerMember(any())).willReturn(MEMBER);

		final OAuthLoginDTO.OAuthLoginResponse OAuthLoginResponse = oAuthLoginService.oauthLogin(ACCESS_TOKEN, MemberType.KAKAO);

		then(memberService).should(times(1)).registerMember(any());
		assertThat(OAuthLoginResponse.getAccessToken()).isNotNull();
		assertThat(OAuthLoginResponse.getRefreshToken()).isNotNull();
	}

	@Test
	void 기존_회원은_토큰을_생성하고_refresh_토큰을_업데이트한다() {
		given(kakaoLoginApiServiceImpl.getUserInfo(ACCESS_TOKEN)).willReturn(KAKAO_OAUTH_ATTRIBUTES);
		given(memberService.findMemberByEmail(anyString())).willReturn(Optional.of(MEMBER));
		given(memberService.registerMember(any())).willReturn(MEMBER);

		final OAuthLoginDTO.OAuthLoginResponse OAuthLoginResponse = oAuthLoginService.oauthLogin(ACCESS_TOKEN, MemberType.KAKAO);

		then(memberService).should().registerMember(any());
		assertThat(OAuthLoginResponse.getAccessToken()).isNotNull();
		assertThat(OAuthLoginResponse.getRefreshToken()).isNotNull();
		assertThat(OAuthLoginResponse.getRefreshTokenExpireTime()).isAfter(new Date());
	}
	//TODO 회원 종류에 따른 테스트 코드 추가
}
