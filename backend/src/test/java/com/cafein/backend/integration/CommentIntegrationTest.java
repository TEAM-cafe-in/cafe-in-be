package com.cafein.backend.integration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
class CommentIntegrationTest extends IntegrationSupporter {

	@Autowired
	private CommentRepository commentRepository;

	@Test
	void 댓글을_등록한다() {
		Long cafeId = 1L;

		ExtractableResponse<Response> responseExtractableResponse =
			post(
				"/api/cafe/" + cafeId + "/comment", generateAccessHeader(access_token),
				"{\"content\": \"댓글을 등록한다.\", \"keywords\": [\"청결도\", \"콘센트\"]}"
			);

		assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	void 존재하지_않는_카페에_댓글을_등록시_에러를_발생한다() {
		Long cafeId = 99999L;

		ExtractableResponse<Response> responseExtractableResponse =
			post(
				"/api/cafe/" + cafeId + "/comment", generateAccessHeader(access_token),
				"{\"content\": \"댓글을 등록한다.\", \"keywords\": [\"청결도\", \"콘센트\"]}"
			);

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorCode"))
				.isEqualTo("C-001"),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorMessage"))
				.isEqualTo("해당 카페는 존재하지 않습니다.")
		);
	}

	@Test
	void 댓글을_수정한다() {
		Long cafeId = 1L;
		Long commentId = 1L;

		ExtractableResponse<Response> responseExtractableResponse =
			patch(
				"/api/cafe/" + cafeId + "/comment/" + commentId, generateAccessHeader(access_token),
				"{\"content\": \"댓글을 수정한다.\", \"keywords\": [\"청결도\", \"콘센트\"]}"
			);

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(responseExtractableResponse.body().asString()).isEqualTo("comment updated"),
			() -> assertThat(commentRepository.findById(commentId).get().getContent()).isEqualTo("댓글을 수정한다.")
		);
	}

	@Test
	void 존재하지_않는_댓글을_수정시_에러가_발생한다() {
		Long cafeId = 1L;
		Long commentId = 9999L;

		ExtractableResponse<Response> responseExtractableResponse =
			patch(
				"/api/cafe/" + cafeId + "/comment/" + commentId, generateAccessHeader(access_token),
				"{\"content\": \"댓글을 수정한다.\", \"keywords\": [\"청결도\", \"콘센트\"]}"
			);

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorCode"))
				.isEqualTo("CO-002"),
			() -> assertThat(responseExtractableResponse.body().jsonPath().getString("errorMessage"))
				.isEqualTo("카페에 해당하는 댓글이 존재하지 않습니다.")
		);
	}

	@Test
	void 댓글을_삭제한다() {
		Long cafeId = 1L;
		Long commentId = 1L;

		ExtractableResponse<Response> responseExtractableResponse =
			delete("/api/cafe/" + cafeId + "/comment/" + commentId, generateAccessHeader(access_token));

		assertAll(
			() -> assertThat(responseExtractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
			() -> assertThat(responseExtractableResponse.body().asString()).isEqualTo("comment deleted"),
			() -> assertThat(commentRepository.findById(commentId).isEmpty()).isTrue()
		);
	}
}

