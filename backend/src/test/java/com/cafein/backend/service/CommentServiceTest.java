package com.cafein.backend.service;

import static com.cafein.backend.api.comment.dto.CommentDTO.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.domain.comment.service.CommentService;
import com.cafein.backend.global.error.exception.EntityNotFoundException;
import com.cafein.backend.support.utils.DataBaseSupporter;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
@Sql("/sql/comment.sql")
class CommentServiceTest extends DataBaseSupporter {

	@Autowired
	private CommentService commentService;

	@SpyBean
	private CommentRepository commentRepository;

	@Test
	void 댓글을_등록한다() {
		final CommentRequest commentRequest = CommentRequest.builder()
			.content("테스트 댓글")
			.keywords(List.of("청결도", "분위기"))
			.build();

		final Long commentId = commentService.addComment(commentRequest, 1L, 1L);
		final Comment comment = commentRepository.findById(2L).get();

		assertThat(commentId).isEqualTo(2L);
		assertThat(comment.getContent()).isEqualTo("테스트 댓글");
		then(commentRepository).should(times(1)).save(any());
	}

	@Test
	void 댓글을_수정한다() {
		final CommentRequest commentRequest = CommentRequest.builder()
			.content("테스트 댓글 수정")
			.keywords(List.of("좌석", "분위기"))
			.build();

		commentService.updateComment(commentRequest, 1L, 1L);

		final Comment comment = commentRepository.findById(1L).get();
		assertThat(comment.getContent()).isEqualTo("테스트 댓글 수정");
	}

	@Test
	void 존재하지_않는_댓글을_삭제하면_예외가_발생한다() {
		assertThatThrownBy(() -> commentService.deleteComment(2L, 2L))
			.isInstanceOf(EntityNotFoundException.class)
			.hasMessage("카페에 해당하는 댓글이 존재하지 않습니다.");
	}

	@Test
	void 댓글을_삭제한다() {
		commentService.deleteComment(1L, 1L);

		then(commentRepository).should(times(1)).deleteById(eq(1L));
	}
}
