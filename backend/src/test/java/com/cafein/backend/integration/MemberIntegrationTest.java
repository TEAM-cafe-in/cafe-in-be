package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.review.respository.ReviewRepository;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
@Sql("/sql/integration.sql")
class MemberIntegrationTest extends IntegrationSupporter {

	private static Member member;
	private static String access_token;

	@Autowired
	private MemberService memberService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ReviewRepository reviewRepository;

	@BeforeEach
	void init() {
		member = memberService.findMemberByMemberId(1L);
		access_token = tokenService.createAccessTokenByRefreshToken(member.getRefreshToken()).getAccessToken();
	}

	@Test
	void 회원_정보를_조회한다() {
		ExtractableResponse<Response> responseExtractableResponse =
			super.get("/api/member/info", generateAccessHeader(access_token));

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(200);
		assertThat(responseExtractableResponse.body().jsonPath().getString("memberName"))
			.isEqualTo(member.getName());
	}

	@Test
	void 잘못된_토큰으로_조회시_에러를_발생한다() {
		ExtractableResponse<Response> responseExtractableResponse =
			super.get("/api/member/info", generateAccessHeader("invalid_token"));

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(401);
		assertThat(responseExtractableResponse.body().jsonPath().getString("errorCode"))
			.isEqualTo("A-002");
		assertThat(responseExtractableResponse.body().jsonPath().getString("errorMessage"))
			.isEqualTo("해당 토큰은 유효한 토큰이 아닙니다.");
	}

	@Test
	void 마이페이지를_조회한다() {
		long reviewCount = reviewRepository.countReviewByMemberId(member.getMemberId());

		ExtractableResponse<Response> responseExtractableResponse =
			super.get("/api/member/mypage", generateAccessHeader(access_token));

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(200);
		assertThat(responseExtractableResponse.body().jsonPath().getString("reviewCount"))
			.isEqualTo(String.valueOf(reviewCount));
	}
}
