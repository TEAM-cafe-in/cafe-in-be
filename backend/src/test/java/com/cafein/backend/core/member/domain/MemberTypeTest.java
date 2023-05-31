package com.cafein.backend.core.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.cafein.backend.domain.member.constant.MemberType;

class MemberTypeTest {

	@Test
	void MemberType이_카카오_타입이면_true를_반환한다() {
		assertThat(MemberType.isMemberType("KAKAO")).isTrue();
	}

	@Test
	void MemberType이_구글_타입이면_true를_반환한다() {
		assertThat(MemberType.isMemberType("GOOGLE")).isTrue();
	}

	@Test
	void MemberType이_구글이나_카카오_타입이_아니면_false를_반환한다() {
		assertThat(MemberType.isMemberType("FACEBOOK")).isFalse();
	}
}
