package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.support.utils.IntegrationTest;

@IntegrationTest
class CafeIntegrationTest extends IntegrationSupporter {

	@Test
	void 메인_화면_조회시_혼잡도를_알_수_없는_카페의_목록을_반환한다() {
		final var response = get("/api/home", generateAccessHeader(access_token));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.jsonPath().getString("cafes[0].averageCongestion")).isEqualTo("0");
	}

	@Test
	void 커피콩을_사용하지_않아_혼잡도_조회_권한이_없는_카페_정보를_반환한다() {
		final var response = get("/api/cafe/2", generateAccessHeader(access_token));

		assertThat(response.jsonPath().getString("cafeInfo.averageCongestion")).isEqualTo("0");
	}

	@Test
	void 커피콩을_사용해_이미_조회한_카페_정보를_반환한다() {
		final var response = get("/api/cafe/3", generateAccessHeader(access_token));

		assertThat(response.jsonPath().getString("cafeInfo.averageCongestion")).isEqualTo("3");
	}

	@Test
	void 커피콩을_사용해서_조회한_카페_정보를_반환한다() {
		final var response = post("/api/cafe/1", generateAccessHeader(access_token));

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(memberService.findMemberByMemberId(1L).getCoffeeBean()).isEqualTo(98),
			() -> assertThat(cafeService.findCafeInfoById(1L, 1L).getCafeInfoProjection().getAverageCongestion())
				.isEqualTo("3")
		);
	}

	@Test
	void 이미_커피콩을_사용해서_조회한_카페에_다시_요청을_보내면_에러를_던진다() {
		final var response = post("/api/cafe/1", generateAccessHeader(access_token));
		final var response2 = post("/api/cafe/1", generateAccessHeader(access_token));

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(response2.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
		);
	}

	@Test
	void 리뷰가_없는_카페는_리뷰의_존재_상태를_false로_반환한다() {
		final var response = get("/api/cafe/4", generateAccessHeader(access_token));

		assertThat(response.jsonPath().getString("cafeInfo.hasReviewed")).isEqualTo("false");
	}

	@Test
	void 리뷰가_없는_카페의_혼잡도_조회_요청은_커피콩을_차감하지_않고_예외를_반환한다() {
		final var response = post("/api/cafe/4", generateAccessHeader(access_token));

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(memberService.findMemberByMemberId(1L).getCoffeeBean()).isEqualTo(100),
			() -> assertThat(response.jsonPath().getString("errorMessage"))
				.isEqualTo("해당 카페에는 리뷰가 존재하지 않아 혼잡도를 확인할 수 없습니다.")
		);
	}
}
