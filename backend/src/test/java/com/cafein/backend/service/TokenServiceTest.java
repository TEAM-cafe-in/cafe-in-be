package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.repository.MemberRepository;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.global.jwt.constant.GrantType;
import com.cafein.backend.global.jwt.service.TokenManager;
import com.cafein.backend.support.fixture.MemberFixture;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class TokenServiceTest {

	@MockBean
	private MemberService memberService;

	@SpyBean
	private TokenManager tokenManager;

	@Autowired
	private TokenService tokenService;

	@Test
	void 액세스_토큰_재발급_테스트() {
		Member createMember = MemberFixture.createMember();
		setField(createMember, "memberId", 1L);
		setField(createMember, "refreshToken", "refresh_token");
		setField(createMember, "tokenExpirationTime", LocalDateTime.now().plusDays(15));
		given(memberService.findMemberByRefreshToken(anyString()))
			.willReturn(createMember);
		given(tokenManager.createAccessTokenExpireTime())
			.willReturn(new Date(System.currentTimeMillis() + Long.parseLong("900000")));

		AccessTokenResponseDTO accessTokenByRefreshToken = tokenService.
			createAccessTokenByRefreshToken(createMember.getRefreshToken());

		then(memberService).should(times(1)).findMemberByRefreshToken(anyString());
		then(tokenManager).should(times(1)).createAccessTokenExpireTime();
		then(tokenManager).should(times(1)).createAccessToken(anyLong(), any(), any(Date.class));
		assertAll(
			() -> assertThat(accessTokenByRefreshToken.getGrantType().toUpperCase()).isEqualTo(GrantType.BEARER.toString()),
			() -> assertThat(accessTokenByRefreshToken.getAccessToken()).isNotNull(),
			() -> assertThat(accessTokenByRefreshToken.getAccessTokenExpireTime()).isAfter(new Date(System.currentTimeMillis()))
		);
	}
}
