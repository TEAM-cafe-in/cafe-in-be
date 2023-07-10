package com.cafein.backend.core.comment.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.cafein.backend.domain.comment.constant.Keyword;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

class KeywordTest {

	@ParameterizedTest(name = "{index} : keyword에 \"{0}\" 입력")
	@ValueSource(strings = {"청결도", "콘센트", "화장실", "메뉴", "좌석", "분위기"})
	void 정상적인_키워드를_입력하면_예외가_발생하지_않는다(String keyword) {
		assertThatNoException().isThrownBy(() -> Keyword.from(keyword));
	}

	@ParameterizedTest(name = "{index} : keyword에 \"{0}\" 입력")
	@ValueSource(strings = {"무드", "뷰", "음악"})
	void 잘못된_키워드를_입력하면_예외가_발생한다(String keyword) {
		assertThatThrownBy(() -> Keyword.from(keyword))
			.isInstanceOf(BusinessException.class)
			.hasMessage(ErrorCode.KEYWORD_NOT_EXIST.getMessage());
	}
}
