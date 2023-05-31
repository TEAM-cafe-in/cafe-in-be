package com.cafein.backend.core.member.domain;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.cafein.backend.global.util.DateTimeUtils;

class MemberTest {

	@Test
	void refresh_Token과_refresh_Token의_만료시간을_업데이트한다() {
		MEMBER.updateRefreshToken(JWT_TOKEN_DTO);

		assertThat(MEMBER.getRefreshToken()).isEqualTo(JWT_TOKEN_DTO.getRefreshToken());
		assertThat(MEMBER.getTokenExpirationTime()).isEqualTo
			(DateTimeUtils.convertToLoalDateTime(JWT_TOKEN_DTO.getRefreshTokenExpireTime()));
	}

	@Test
	void refresh_Token을_만료처리한다() {
		MEMBER.expireRefreshToken(LocalDateTime.now());

		assertThat(MEMBER.getTokenExpirationTime()).isBefore(LocalDateTime.now());
	}
}
