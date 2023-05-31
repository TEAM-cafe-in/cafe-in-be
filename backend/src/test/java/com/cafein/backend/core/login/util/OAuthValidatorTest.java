package com.cafein.backend.core.login.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cafein.backend.api.login.validator.OAuthValidator;
import com.cafein.backend.global.error.exception.BusinessException;

class OAuthValidatorTest {

	@ParameterizedTest
	@ValueSource(strings = {"KAKAO", "GOOGLE"})
	void 카카오나_구글_회원은_검증을_통과한다(String memberType) {
		assertThatNoException().isThrownBy(() -> OAuthValidator.validateMemberType(memberType));
	}

	@ParameterizedTest
	@ValueSource(strings = {"FACEBOOK", "NAVER"})
	void 카카오나_구글_회원이_아니면_예외가_발생한다(String memberType) {
		assertThatThrownBy(() -> OAuthValidator.validateMemberType(memberType))
			.isInstanceOf(BusinessException.class)
			.hasMessage("잘못된 회원 타입 입니다.");
	}
}
