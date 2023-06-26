package com.cafein.backend.api.member.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MyPageDTO {

	private List<CafeInfoViewedByMemberProjection> cafeInfoViewedByMemberDTOS;
	private List<MemberReviewProjection> reviewDTOS;

	@Schema(description = "총 리뷰 수", example = "10", required = true)
	private long reviewCount;

	public static MyPageDTO of(final List<CafeInfoViewedByMemberProjection> cafeInfoViewedByMemberDTOs,
		final List<MemberReviewProjection> reviewDTOs, final long reviewCount) {
		return MyPageDTO.builder()
			.cafeInfoViewedByMemberDTOS(cafeInfoViewedByMemberDTOs)
			.reviewDTOS(reviewDTOs)
			.reviewCount(reviewCount)
			.build();
	}

	@Override
	public String toString() {
		return "MyPageDTO{" +
			"cafeInfoViewedByMemberDTOS=" + cafeInfoViewedByMemberDTOS +
			", reviewDTOS=" + reviewDTOS +
			", reviewCount=" + reviewCount +
			'}';
	}
}
