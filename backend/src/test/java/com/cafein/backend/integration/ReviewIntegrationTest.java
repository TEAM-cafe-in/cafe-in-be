package com.cafein.backend.integration;

import static com.cafein.backend.support.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.support.utils.IntegrationTest;

@IntegrationTest
class ReviewIntegrationTest extends IntegrationSupporter {

	@Test
	void 카페에_대한_리뷰를_등록한다() {
		final var response = post("/api/cafe/4/review", generateAccessHeader(access_token), REVIEW_REQUEST);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(response.body().jsonPath().getString("reviewId")).isNotNull(),
			() -> assertThat(memberService.findMemberByMemberId(1L).getCoffeeBean()).isEqualTo(102)
		);
	}

	@Test
	void 동일_카페에_대해_하루에_한_번만_리뷰를_등록_가능하다() {
		final var response = post("/api/cafe/3/review", generateAccessHeader(access_token), REVIEW_REQUEST);

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(response.body().jsonPath().getString("errorMessage"))
				.isEqualTo("해당 카페에 대해 하루에 한번만 리뷰를 작성할 수 있습니다.")
		);
	}
}
