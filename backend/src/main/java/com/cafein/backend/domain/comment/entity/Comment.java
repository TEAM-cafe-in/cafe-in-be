package com.cafein.backend.domain.comment.entity;

import static javax.persistence.FetchType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(nullable = false)
	@Lob
	private String content;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "cafe_id")
	private Cafe cafe;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public Comment(String content, Cafe cafe, Member member) {
		this.content = content;
		this.cafe = cafe;
		this.member = member;
	}
}
