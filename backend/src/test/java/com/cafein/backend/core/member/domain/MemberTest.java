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
			(DateTimeUtils.convertDateToLocalDateTime(JWT_TOKEN_DTO.getRefreshTokenExpireTime()));
	}

	@Test
	void refresh_Token을_만료처리한다() {
		MEMBER.expireRefreshToken(LocalDateTime.now());

		assertThat(MEMBER.getTokenExpirationTime()).isBefore(LocalDateTime.now());
	}

	@Test
	void 카페를_조회하면_커피콩을_차감한다() {
		MEMBER.subtractCoffeeBean(100);

		assertThat(MEMBER.getCoffeeBean()).isEqualTo(98);
	}

	@Test
	void 리뷰를_등록하면_커피콩을_추가한다() {
		MEMBER.addCoffeeBean(100);

		assertThat(MEMBER.getCoffeeBean()).isEqualTo(102);
	}

	@Test
	void 회원의_닉네임을_수정한다() {
		MEMBER.updateName("손흥민");

		assertThat(MEMBER.getName()).isEqualTo("손흥민");
	}
}
