package com.cafein.backend.domain.commentkeyword.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafein.backend.domain.commentkeyword.entity.CommentKeyword;

public interface CommentKeywordRepository extends JpaRepository<CommentKeyword, Long> {

	@Query("select ck from CommentKeyword ck where ck.comment.commentId = :commentId")
	List<CommentKeyword> findAllByCommentId(@Param("commentId") Long commentId);

	@Modifying
	@Query("delete from CommentKeyword ck where ck.comment.commentId = :commentId")
	void deleteAllByCommentId(@Param("commentId") Long commentId);
}
