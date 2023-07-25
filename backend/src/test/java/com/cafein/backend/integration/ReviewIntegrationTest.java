package com.cafein.backend.integration;

import static com.cafein.backend.support.fixture.ReviewFixture.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class ReviewIntegrationTest extends IntegrationSupporter {

	@Autowired
	private MemberService memberService;

	@Test
	void 카페에_대한_리뷰를_등록한다() {
		final ExtractableResponse<Response> response = post("/api/cafe/4/review", generateAccessHeader(access_token), REVIEW_REQUEST);

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
			() -> assertThat(response.body().jsonPath().getString("reviewId")).isNotNull(),
			() -> assertThat(memberService.findMemberByMemberId(1L).getCoffeeBean()).isEqualTo(102)
		);
	}
}
