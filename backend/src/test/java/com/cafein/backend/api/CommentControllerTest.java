package com.cafein.backend.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.cafein.backend.api.comment.controller.CommentController;

class CommentControllerTest extends ControllerTestSupporter {

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

	@Test
	void 댓글_등록시_키워드가_없어도_댓글_등록에_성공한다() throws Exception {
		given(commentService.addComment(any(), eq(1L), anyLong())).willReturn(1L);

		mockMvc(new CommentController(commentService))
			.perform(
				post("/api/cafe/1/comment")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"content\":\"여기 카페는 분위기가 좋아요!\"}")
			)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cafe/1/comment/1"));
	}

	@Test
	void 댓글을_등록한다() throws Exception {
		given(commentService.addComment(any(), eq(1L), anyLong())).willReturn(1L);

		mockMvc(new CommentController(commentService))
			.perform(
				post("/api/cafe/1/comment")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"content\":\"여기 카페는 분위기가 좋아요!\","
						+ "\"keywords\":[\"청결도\",\"콘센트\",\"분위기\"]}")
			)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cafe/1/comment/1"));
	}

	@Test
	void 댓글을_수정한다() throws Exception {
		mockMvc(new CommentController(commentService))
			.perform(
				patch("/api/cafe/1/comment/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content("{\"content\":\"여기 카페는 분위기가 좋아요!\","
						+ "\"keywords\":[\"청결도\",\"콘센트\",\"분위기\"]}")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value("comment updated"));
	}

	@Test
	void 댓글을_삭제한다() throws Exception {
		mockMvc(new CommentController(commentService))
			.perform(
				delete("/api/cafe/1/comment/1")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value("comment deleted"));
	}
}
