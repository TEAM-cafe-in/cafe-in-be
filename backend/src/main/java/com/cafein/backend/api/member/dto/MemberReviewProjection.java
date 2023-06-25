package com.cafein.backend.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface MemberReviewProjection {

	@Schema(description = "카페 이름", example = "5to7", required = true)
	String getCafeName();

	@Schema(description = "카페 주소", example = "서울시 성동구 서울숲2길44-13 1층", required = true)
	String getAddress();

	@Schema(description = "카페 혼잡도", example = "MEDIUM", required = true)
	String getCafeCongestion();

	@Schema(description = "카페가 청결한지에 대한 리뷰", example = "true", required = true)
	String getIsClean();

	@Schema(description = "카페에 플러그가 있는지에 대한 리뷰", example = "true", required = true)
	String getHasPlug();
}
