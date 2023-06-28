package com.cafein.backend.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.comment.dto.CommentDTO;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.domain.comment.constant.Keyword;
import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.domain.commentkeyword.entity.CommentKeyword;
import com.cafein.backend.domain.commentkeyword.repository.CommentKeywordRepository;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final CommentKeywordRepository commentKeywordRepository;
	private final MemberService memberService;
	private final CafeService cafeService;

	public void addComment(final CommentDTO.Request requestDTO, final Long cafeId, final Long memberId) {
		Comment comment = createCafeComment(requestDTO, cafeId, memberId);
		commentRepository.save(comment);
	}

	private Comment createCafeComment(final CommentDTO.Request requestDTO, final Long cafeId, final Long memberId) {
		final Member member = memberService.findMemberByMemberId(memberId);
		final Cafe cafe = cafeService.findCafeByCafeId(cafeId);
		Comment comment = Comment.builder()
			.content(requestDTO.getContent())
			.cafe(cafe)
			.member(member)
			.build();
		for (String key : requestDTO.getKeywords()) {
			commentKeywordRepository.save(createCommentKeyword(comment, key));
		}
		return comment;
	}

	private CommentKeyword createCommentKeyword(final Comment comment, final String key) {
		return CommentKeyword.builder()
			.keyword(Keyword.from(key))
			.comment(comment)
			.build();
	}
}
