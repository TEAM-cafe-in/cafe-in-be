package com.cafein.backend.api.comment.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class CommentDTO {

	@Getter @Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		@Schema(name = "content", description = "댓글 내용", example = "여기 카페 너무 트렌디해요!", required = true)
		@NotNull
		private String content;

		@Schema(name = "keywords", description = "댓글 키워드", example = "[청결도, 콘센트, 화장실, 메뉴, 좌석, 분위기]", required = false)
		private List<String> keywords;
	}
}
