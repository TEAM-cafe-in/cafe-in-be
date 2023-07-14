package com.cafein.backend.support.fixture;

import com.cafein.backend.api.review.dto.ReviewDTO;

public class ReviewFixture {

	public static final ReviewDTO.ReviewResponse REVIEW_RESPONSE = createReviewResponse();

	private static ReviewDTO.ReviewResponse createReviewResponse() {
		return ReviewDTO.ReviewResponse.builder()
			.coffeeBean(102)
			.build();
	}
}
