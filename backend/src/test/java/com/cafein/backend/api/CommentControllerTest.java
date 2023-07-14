package com.cafein.backend.api;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import com.cafein.backend.api.comment.controller.CommentController;
import com.cafein.backend.domain.comment.service.CommentService;

class CommentControllerTest extends ControllerTestSupporter {

	@Mock
	private CommentService commentService;

	@Test
	void 댓글_등록시_내용을_입력하지_않으면_예외가_발생한다() throws Exception {
		mockMvc(new CommentController(commentService))
			.perform(
				post("/api/cafe/1/comment")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString("Required request body is missing")));
	}
}
