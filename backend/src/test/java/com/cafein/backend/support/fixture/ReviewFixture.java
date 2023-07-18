package com.cafein.backend.support.fixture;

import static com.cafein.backend.api.review.dto.ReviewDTO.*;

import java.util.List;

import com.cafein.backend.domain.review.constant.CafeCongestion;
import com.cafein.backend.domain.review.entity.Review;

public class ReviewFixture {

	public static final Review REVIEW = createReview();
	public static final ReviewRequest REVIEW_REQUEST = createReviewRequest();
	public static final List<Long> CAFE_IDS_OF_RECENT_REVIEWS = List.of(1L, 2L, 3L);

	public static final ReviewResponse REVIEW_RESPONSE = createReviewResponse();

	private static Review createReview() {
		return Review.builder()
			.cafeCongestion(CafeCongestion.HIGH)
			.isClean(true)
			.hasPlug(true)
			.build();
	}

	private static ReviewRequest createReviewRequest() {
		return ReviewRequest.builder()
			.cafeCongestion("3")
			.isClean("true")
			.hasPlug("true")
			.build();
	}

		private static ReviewResponse createReviewResponse() {
		return ReviewResponse.builder()
			.reviewId(1L)
			.coffeeBean(102)
			.build();
	}
}
