package com.cafein.backend.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CafeInfoViewedByMemberProjection {

	@Schema(description = "카페 이름", example = "5to7", required = true)
	String getCafeName();

	@Schema(description = "카페 주소", example = "서울시 성동구 서울숲2길44-13 1층", required = true)
	String getAddress();

	@Schema(description = "카페 리뷰 개수", example = "10", required = true)
	String getCommentReviewCount();

	void setCafeName(String cafeName);
	void setAddress(String address);
	void setCommentReviewCount(String commentReviewCount);
}
