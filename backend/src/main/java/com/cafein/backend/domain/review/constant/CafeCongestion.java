package com.cafein.backend.domain.review.constant;

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
