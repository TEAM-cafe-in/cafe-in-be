package com.cafein.backend.domain.comment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafein.backend.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("select co from Comment co where co.cafe.cafeId = :cafeId and co.commentId = :commentId")
	Optional<Comment> findCommentByCafeId(@Param("cafeId")Long cafeId, @Param("commentId")Long commentId);
}
