package com.cafein.backend.domain.commentkeyword.entity;

import static javax.persistence.FetchType.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cafein.backend.domain.comment.constant.Keyword;
import com.cafein.backend.domain.comment.entity.Comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentKeywordId;

	@Enumerated(EnumType.STRING)
	private Keyword keyword;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "comment_id")
	private Comment comment;

	@Builder
	public CommentKeyword(final Keyword keyword, final Comment comment) {
		this.keyword = keyword;
		this.comment = comment;
	}
}
