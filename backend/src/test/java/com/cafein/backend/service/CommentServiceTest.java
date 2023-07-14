package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;

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
