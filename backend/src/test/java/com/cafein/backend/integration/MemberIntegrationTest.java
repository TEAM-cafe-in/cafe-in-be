package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class MemberIntegrationTest extends IntegrationSupporter {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Test
	void 회원_정보를_조회한다() {
		ExtractableResponse<Response> responseExtractableResponse =
			get("/api/member/info", generateAccessHeader(access_token));

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseExtractableResponse.body().jsonPath().getString("memberName"))
			.isEqualTo(member.getName());
	}

	@Test
	void 잘못된_토큰으로_조회시_에러를_발생한다() {
		ExtractableResponse<Response> responseExtractableResponse =
			get("/api/member/info", generateAccessHeader("invalid_token"));

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value()),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorCode")).isEqualTo("A-002"),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorMessage"))
				.isEqualTo("해당 토큰은 유효한 토큰이 아닙니다.")
		);
	}

	@Test
	void 마이페이지를_조회한다() {
		long reviewCount = reviewRepository.countReviewByMemberId(member.getMemberId());

		ExtractableResponse<Response> responseExtractableResponse =
			get("/api/member/mypage", generateAccessHeader(access_token));

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(responseExtractableResponse.body().jsonPath().getString("reviewCount"))
			.isEqualTo(String.valueOf(reviewCount));
	}

	@Test
	void 닉네임을_수정한다() {
		ExtractableResponse<Response> responseExtractableResponse = patch("/api/member/name", generateAccessHeader(access_token),
			"{\"name\": \"newName\"}");

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() ->assertThat(memberService.findMemberByMemberId(member.getMemberId()).getName()).isEqualTo("newName"),
			() ->assertThat(responseExtractableResponse.body().asString()).isEqualTo("Name change successful!")
		);
	}
}
