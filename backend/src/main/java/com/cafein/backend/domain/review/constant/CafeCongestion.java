package com.cafein.backend.domain.review.constant;

import java.util.Arrays;

import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

import lombok.Getter;

@Getter
public enum CafeCongestion {

	LOW("1"),
	MEDIUM("2"),
	HIGH("3");

	private final String cafeCongestion;

	CafeCongestion(final String cafeCongestion) {
		this.cafeCongestion = cafeCongestion;
	}

	public static CafeCongestion from(String cafeCongestion) {
		return Arrays.stream(CafeCongestion.values())
			.filter(value -> value.getCafeCongestion().equals(cafeCongestion))
			.findFirst()
			.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CAFE_CONGESTION_VALUE));
	}
}
