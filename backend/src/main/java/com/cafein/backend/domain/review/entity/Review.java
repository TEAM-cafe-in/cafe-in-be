package com.cafein.backend.domain.review.entity;

import static javax.persistence.FetchType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.review.constant.CafeCongestion;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "cafe_id")
	private Cafe cafe;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Review(final CafeCongestion cafeCongestion, final boolean isClean, final boolean hasPlug,
		Cafe cafe, Member member) {
		this.cafeCongestion = cafeCongestion;
		this.isClean = isClean;
		this.hasPlug = hasPlug;
		this.cafe = cafe;
		this.member = member;
	}
}
