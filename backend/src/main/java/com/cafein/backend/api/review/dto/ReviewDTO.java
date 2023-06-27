package com.cafein.backend.api.review.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class ReviewDTO {

	@Getter @Builder
	public static class Request {

		@NotNull
		@Schema(description = "카페 혼잡도. HIGH, MEDIUM, LOW 중 하나입니다.", example = "HIGH", required = true)
		private String cafeCongestion;

		@NotNull
		@Schema(description = "청결도", example = "true", required = true)
		private String isClean;

		@NotNull
		@Schema(description = "콘센트 자리 유무", example = "true", required = true)
		private String hasPlug;
	}

	@Getter @Builder
	public static class Response {

		@Schema(description = "커피빈", example = "100", required = true)
		private Integer coffeeBean;
	}
}
