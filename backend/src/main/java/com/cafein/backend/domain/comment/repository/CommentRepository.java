package com.cafein.backend.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
