package com.cafein.backend.domain.viewedcafe.entity;

import static javax.persistence.FetchType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ViewedCafe extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long viewedCafeId;

	@Column(nullable = false)
	private Long cafeId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;

	@Builder
	public ViewedCafe(final Long cafeId, final Member member) {
		this.cafeId = cafeId;
		this.member = member;
	}
}
