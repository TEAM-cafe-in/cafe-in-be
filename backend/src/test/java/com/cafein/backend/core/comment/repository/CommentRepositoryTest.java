package com.cafein.backend.core.comment.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.support.utils.RepositoryTest;

@RepositoryTest
class CommentRepositoryTest {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	@Rollback
	@Sql(value = "/sql/comment-repository.sql")
	void 카페_Id로_댓글을_조회한다() {
		final Optional<Comment> comment = commentRepository.findCommentByCafeId(1L, 1L);

		assertThat(comment.isPresent()).isTrue();
		assertThat(comment.get().getCommentId()).isEqualTo(1L);
	}
}
