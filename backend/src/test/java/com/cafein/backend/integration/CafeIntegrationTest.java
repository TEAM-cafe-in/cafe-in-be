package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class CafeIntegrationTest extends IntegrationSupporter {

	@Test
	void 메인_화면에_필요한_카페를_반환한다() {
		final ExtractableResponse<Response> response = get("/api/home",
			generateAccessHeader(access_token));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
