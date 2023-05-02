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
import com.cafein.backend.domain.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(nullable = false)
	@Lob
	private String content;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "cafe_id")
	private Cafe cafe;
}
