package com.cafein.backend.domain.comment.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

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
		log.debug("requestDTO.getKeywords(): {}", requestDTO.getKeywords());
		final Member member = memberService.findMemberByMemberId(memberId);
		final Cafe cafe = cafeService.findCafeByCafeId(cafeId);
		Comment comment = Comment.builder()
			.content(requestDTO.getContent())
			.cafe(cafe)
			.member(member)
			.build();
		addCommentKeyword(requestDTO, comment);
		return comment;
	}

	private void addCommentKeyword(final CommentDTO.Request requestDTO, final Comment comment) {
		for (String key : requestDTO.getKeywords()) {
			commentKeywordRepository.save(createCommentKeyword(comment, key));
		}
	}

	private CommentKeyword createCommentKeyword(final Comment comment, final String key) {
		return CommentKeyword.builder()
			.keyword(Keyword.from(key))
			.comment(comment)
			.build();
	}

	public void deleteComment(final Long cafeId, final Long commentId) {
		validateCafeComment(cafeId, commentId);
		commentRepository.deleteById(commentId);
	}

	private void validateCafeComment(final Long cafeId, final Long commentId) {
		commentRepository.findCommentByCafeId(cafeId, commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
	}

	public void updateComment(final CommentDTO.Request requestDTO, final Long cafeId, final Long commentId) {
		final List<CommentKeyword> oldKeywords = commentKeywordRepository.findAllByCommentId(commentId);

		final Set<String> oldCommentKeywords = oldKeywords.stream()
			.map((commentKeyword) -> commentKeyword.getKeyword().getKeyWord())
			.collect(Collectors.toCollection(HashSet::new));

		final Comment comment = commentRepository.findCommentByCafeId(cafeId, commentId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));

		log.debug("oldCommentKeywords: {}", oldCommentKeywords);
		log.debug("newCommentKeywords(): {}", requestDTO.getKeywords());

		if(!new HashSet<>(requestDTO.getKeywords()).equals(oldCommentKeywords)) {
			updateCommentKeyword(requestDTO, comment);
		}
		comment.updateContent(requestDTO.getContent());
	}

	private void updateCommentKeyword(final CommentDTO.Request requestDTO, final Comment comment) {
		commentKeywordRepository.deleteAllByCommentId(comment.getCommentId());
		addCommentKeyword(requestDTO, comment);
	}
}
