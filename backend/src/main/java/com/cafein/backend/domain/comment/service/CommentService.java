package com.cafein.backend.domain.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	@Transactional(readOnly = true)
	public Integer countByCafeId(Long cafeId) {
		return commentRepository.countCommentByCafeId(cafeId);
	}
}
