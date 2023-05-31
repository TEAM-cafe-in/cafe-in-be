package com.cafein.backend.core.login.util;

import static com.cafein.backend.support.fixture.LoginFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.cafein.backend.global.error.exception.AuthenticationException;
import com.cafein.backend.global.util.AuthorizationHeaderUtils;

class AuthorizationHeaderUtilsTest {

	@ParameterizedTest(name = "{index} : authorizationHeader에 \"{0}\" 입력")
	@NullAndEmptySource
	void authorizationHeader가_공백이거나_NULL이면_예외를_발생시킨다(String authorizationHeader) {
		assertThatThrownBy(() -> AuthorizationHeaderUtils.validateAuthorization(authorizationHeader))
			.isInstanceOf(AuthenticationException.class)
			.hasMessage("Authorization Header가 빈값입니다.");
	}

	@ParameterizedTest(name = "{index} : authorizationHeader에 \"{0}\" 입력")
	@ValueSource(strings = {"Basic amlubnk6bGFtcA==", "Bearer"})
	void authorizationHeader가_Bearer_타입이_아니거나_access_token이_없으면_예외를_발생시킨다(String authorizationHeader) {
		assertThatThrownBy(() -> AuthorizationHeaderUtils.validateAuthorization(authorizationHeader))
			.isInstanceOf(AuthenticationException.class)
			.hasMessage("인증 타입이 Bearer 타입이 아닙니다.");
	}

	@Test
	void 정상적인_authorizationHeader_요청시_예외가_발생하지_않는다() {
		assertThatNoException().isThrownBy(() -> AuthorizationHeaderUtils.validateAuthorization(AUTHORIZATION_HEADER_ACCESS));
	}
}
