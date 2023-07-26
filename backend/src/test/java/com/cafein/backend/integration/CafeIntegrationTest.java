package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class CafeIntegrationTest extends IntegrationSupporter {

	@Test
	void 메인_화면_조회시_혼잡도를_알_수_없는_카페의_목록을_반환한다() {
		final ExtractableResponse<Response> response = get("/api/home",
			generateAccessHeader(access_token));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.jsonPath().getString("cafes[0].averageCongestion")).isEqualTo("0");
	}

	@Test
	void 커피콩을_사용하지_않아_혼잡도_조회_권한이_없는_카페_정보를_반환한다() {
		final ExtractableResponse<Response> response = get("/api/cafe/2", generateAccessHeader(access_token));

		assertThat(response.jsonPath().getString("cafeInfoProjection.averageCongestion")).isEqualTo("0");
	}

	@Test
	void 커피콩을_사용해_이미_조회한_카페_정보를_반환한다() {
		final ExtractableResponse<Response> response = get("/api/cafe/3", generateAccessHeader(access_token));

		assertThat(response.jsonPath().getString("cafeInfoProjection.averageCongestion")).isEqualTo("3");
	}

	@Test
	void 커피콩을_사용해서_조회한_카페_정보를_반환한다() {
		final ExtractableResponse<Response> response = post("/api/cafe/1", generateAccessHeader(access_token));

		Assertions.assertAll(
			() -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(memberService.findMemberByMemberId(1L).getCoffeeBean()).isEqualTo(98),
			() -> assertThat(cafeService.findCafeInfoById(1L, 1L).getCafeInfoProjection().getAverageCongestion())
				.isEqualTo("3")
		);
	}
}
