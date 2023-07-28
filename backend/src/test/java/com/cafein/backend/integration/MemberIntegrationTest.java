package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.support.utils.IntegrationTest;

@IntegrationTest
class MemberIntegrationTest extends IntegrationSupporter {

	@Test
	void 회원_정보를_조회한다() {
		final var response = get("/api/member/info", generateAccessHeader(access_token));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.body().jsonPath().getString("memberName")).isEqualTo(member.getName());
	}

	@Test
	void 잘못된_토큰으로_조회시_에러를_발생한다() {
		final var response = get("/api/member/info", generateAccessHeader("invalid_token"));

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
			() -> assertThat(response.body().jsonPath().getString("errorCode")).isEqualTo("A-002"),
			() -> assertThat(response.body().jsonPath().getString("errorMessage"))
				.isEqualTo("해당 토큰은 유효한 토큰이 아닙니다.")
		);
	}

	@Test
	void 마이페이지를_조회한다() {
		long reviewCount = reviewRepository.countReviewByMemberId(member.getMemberId());

		final var response = get("/api/member/mypage", generateAccessHeader(access_token));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.body().jsonPath().getString("reviewCount"))
			.isEqualTo(String.valueOf(reviewCount));
	}

	@Test
	void 닉네임을_수정한다() {
		final var response = patch("/api/member/name", generateAccessHeader(access_token),
			"{\"name\": \"newName\"}");

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() ->assertThat(memberService.findMemberByMemberId(member.getMemberId()).getName()).isEqualTo("newName"),
			() ->assertThat(response.body().asString()).isEqualTo("Name change successful!")
		);
	}

	@Test
	void 회원의_커피콩을_조회한다() {
		final var response = get("/api/member/coffeebean", generateAccessHeader(access_token));

		assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response.body().asString()).isEqualTo("100")
		);
	}
}
