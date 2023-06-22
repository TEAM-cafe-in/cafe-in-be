package com.cafein.backend.api.member.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MyPageDTO {

	private List<CafeInfoViewedByMemberProjection> cafeInfoViewedByMemberDTOS;
	private List<MemberReviewProjection> reviewDTOS;
	private long reviewCount;

	public static MyPageDTO of(final List<CafeInfoViewedByMemberProjection> cafeInfoViewedByMemberDTOs,
		final List<MemberReviewProjection> reviewDTOs, final long reviewCount) {
		return MyPageDTO.builder()
			.cafeInfoViewedByMemberDTOS(cafeInfoViewedByMemberDTOs)
			.reviewDTOS(reviewDTOs)
			.reviewCount(reviewCount)
			.build();
	}
}
