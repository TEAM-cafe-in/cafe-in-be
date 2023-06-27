package com.cafein.backend.domain.comment.constant;

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
}
