package com.cafein.backend.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	/**
	 * 주소
	 *
	 * @Param sido : 경기
	 * @Param sigungu: 성남시 분당구
	 * @Param roadName: 판교역로
	 * @Param houseNumber: 32-5
	 */
	@Column(nullable = false)
	private String sido;

	@Column(nullable = false)
	private String sigungu;

	@Column(nullable = false)
	private String roadName;

	@Column(nullable = false)
	private String houseNumber;

	@Builder
	public Address(String sido, String sigungu, String roadName, String houseNumber) {
		this.sido = sido;
		this.sigungu = sigungu;
		this.roadName = roadName;
		this.houseNumber = houseNumber;
	}
}
