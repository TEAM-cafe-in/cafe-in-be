package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.ReviewFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import com.cafein.backend.api.review.controller.ReviewController;
import com.cafein.backend.support.fixture.LoginFixture;

class ReviewControllerTest extends ControllerTestSupporter {

	@Test
	void 리뷰를_등록한다() throws Exception {
		given(reviewService.createReview(any(), anyLong(), any()))
			.willReturn(REVIEW_RESPONSE);

		mockMvc(new ReviewController(reviewService))
			.perform(post("/api/cafe/1/review")
				.header(HttpHeaders.AUTHORIZATION, LoginFixture.AUTHORIZATION_HEADER_ACCESS)
				.content("{\"cafeCongestion\": \"3\", \"isClean\": \"true\", \"hasPlug\": \"true\"}")
				.contentType(APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", "/api/cafe/1/review/1"));
	}
}
