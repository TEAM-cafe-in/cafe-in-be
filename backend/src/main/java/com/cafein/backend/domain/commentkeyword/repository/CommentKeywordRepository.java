package com.cafein.backend.domain.commentkeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.commentkeyword.entity.CommentKeyword;

public interface CommentKeywordRepository extends JpaRepository<CommentKeyword, Long> {
}
