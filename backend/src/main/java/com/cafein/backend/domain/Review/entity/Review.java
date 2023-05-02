package com.cafein.backend.domain.Review.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cafein.backend.domain.Review.constant.CafeCongestion;
import com.cafein.backend.domain.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CafeCongestion cafeCongestion;

	@Column(nullable = false)
	private boolean isClean;

	@Column(nullable = false)
	private boolean hasPlug;

	@Builder
	public Review(final CafeCongestion cafeCongestion, final boolean isClean, final boolean hasPlug) {
		this.cafeCongestion = cafeCongestion;
		this.isClean = isClean;
		this.hasPlug = hasPlug;
	}
	//TODO 리뷰 등록 시 커피콩 더해주는 로직 구현
}
