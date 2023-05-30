package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.api.login.service.OAuthLoginService;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.external.oauth.google.service.GoogleLoginApiServiceImpl;
import com.cafein.backend.external.oauth.kakao.service.KakaoLoginApiServiceImpl;
import com.cafein.backend.support.utils.DataBaseSupporter;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class OAuthLoginServiceTest extends DataBaseSupporter {

	@Autowired
	private OAuthLoginService oAuthLoginService;

	@MockBean
	private KakaoLoginApiServiceImpl kakaoLoginApiServiceImpl;

	@MockBean
	private GoogleLoginApiServiceImpl googleLoginApiServiceImpl;

	@Test
	void 카카오_로그인을_시도하면_true를_반환한다() {
		given(kakaoLoginApiServiceImpl.getUserInfo(ACCESS_TOKEN)).willReturn(KAKAO_OAUTH_ATTRIBUTES);
		assertThatNoException().isThrownBy(() -> oAuthLoginService.oauthLogin(ACCESS_TOKEN, MemberType.KAKAO));
	}

	@Test
	void 구글_로그인을_시도하면_true를_반환한다() {
		given(googleLoginApiServiceImpl.getUserInfo(ACCESS_TOKEN)).willReturn(GOOGLE_OAUTH_ATTRIBUTES);
		assertThatNoException().isThrownBy(() -> oAuthLoginService.oauthLogin(ACCESS_TOKEN, MemberType.GOOGLE));
	}

	@Test
	void 로그인을_하면_access_Token과_refresh_Token을_발급한다() {
		given(kakaoLoginApiServiceImpl.getUserInfo(ACCESS_TOKEN)).willReturn(KAKAO_OAUTH_ATTRIBUTES);

		final OAuthLoginDTO.Response response = oAuthLoginService.oauthLogin(ACCESS_TOKEN, MemberType.KAKAO);

		assertThat(response.getAccessToken()).isNotNull();
		assertThat(response.getRefreshToken()).isNotNull();
	}
}
