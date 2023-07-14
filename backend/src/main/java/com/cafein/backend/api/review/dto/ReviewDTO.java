package com.cafein.backend.api.review.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewDTO {

	@Getter @Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReviewRequest {

		@NotNull
		@Schema(description = "카페 혼잡도. 1(LOW), 2(MEDIUM), 3(HIGH) 중 하나입니다.", example = "1", required = true)
		private String cafeCongestion;

		@NotNull
		@Schema(description = "청결도", example = "true", required = true)
		private String isClean;

		@NotNull
		@Schema(description = "콘센트 자리 유무", example = "true", required = true)
		private String hasPlug;
	}

	@Getter @Builder
	public static class ReviewResponse {

		@Schema(description = "커피빈", example = "100", required = true)
		private Integer coffeeBean;
	}
}
