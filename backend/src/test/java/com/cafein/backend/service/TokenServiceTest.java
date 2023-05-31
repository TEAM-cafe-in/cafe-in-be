package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.global.jwt.service.TokenManager;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class TokenServiceTest {

	@Autowired
	private TokenService tokenService;

	@MockBean
	private MemberService memberService;

	@SpyBean
	private TokenManager tokenManager;

	@Test
	void access_Token_재발급을_테스트한다() {
		given(memberService.findMemberByRefreshToken(anyString())).willReturn(MEMBER);
		given(tokenManager.createAccessTokenExpireTime()).willReturn(
			new Date(System.currentTimeMillis() + Long.parseLong("900000")));

		AccessTokenResponseDTO accessTokenResponseDTO = tokenService.
			createAccessTokenByRefreshToken(MEMBER.getRefreshToken());

		then(tokenManager).should(times(1)).createAccessToken(anyLong(), any(), any(Date.class));

		assertThat(accessTokenResponseDTO.getAccessToken()).isNotNull();
		assertThat(accessTokenResponseDTO.getAccessTokenExpireTime()).isAfter(new Date(System.currentTimeMillis()));
	}
}
