package com.cafein.backend.domain.comment.constant;

import java.util.Arrays;

import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

import lombok.Getter;

@Getter
public enum Keyword {

	CLEAN("청결도"),
	PLUG("콘센트"),
	RESTROOM("화장실"),
	MENU("메뉴"),
	SEAT("좌석"),
	MOOD("분위기");

	private final String keyWord;

	Keyword(final String keyWord) {
		this.keyWord = keyWord;
	}

	public static Keyword from(String keyword) {
		return Arrays.stream(Keyword.values())
			.filter(value -> value.getKeyWord().equals(keyword))
			.findFirst()
			.orElseThrow(() -> new BusinessException(ErrorCode.KEYWORD_NOT_EXIST));
	}
}
