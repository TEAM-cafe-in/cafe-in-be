package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.external.oauth.google.service.GoogleLoginApiServiceImpl;
import com.cafein.backend.external.oauth.kakao.service.KakaoLoginApiServiceImpl;
import com.cafein.backend.external.oauth.service.SocialLoginApiServiceFactory;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
public class SocialLoginApiServiceFactoryTest {

	@Test
	void 카카오_로그인시_해당_빈을_가져온다() {
		assertThat(SocialLoginApiServiceFactory.getSocialLoginApiService(MemberType.KAKAO))
			.isInstanceOf(KakaoLoginApiServiceImpl.class);
	}

	@Test
	void 구글_로그인시_해당_빈을_가져온다() {
		assertThat(SocialLoginApiServiceFactory.getSocialLoginApiService(MemberType.GOOGLE))
			.isInstanceOf(GoogleLoginApiServiceImpl.class);
	}
}
