package com.cafein.backend.core.review.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cafein.backend.domain.review.constant.CafeCongestion;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

class CafeCongestionTest {

	@ParameterizedTest(name = "{index}: cafeCongestion에 \"{0}\" 입력")
	@ValueSource(strings = {"1", "2", "3"})
	void 카페_혼잡도를_1_2_3의_숫자로_입력받는다(String cafeCongestion) {
		assertThatNoException().isThrownBy(() -> CafeCongestion.from(cafeCongestion));
	}

	@ParameterizedTest(name = "{index}: {0}은 카페 혼잡도에 해당하지 않는다")
	@ValueSource(strings = {"여유", "보통", "혼잡"})
	void 카페_혼잡도가_1_2_3이_아니라면_예외가_발생한다(String cafeCongestion) {
		assertThatThrownBy(() -> CafeCongestion.from(cafeCongestion))
			.isInstanceOf(BusinessException.class)
			.hasMessage(ErrorCode.INVALID_CAFE_CONGESTION_VALUE.getMessage());
	}
}
