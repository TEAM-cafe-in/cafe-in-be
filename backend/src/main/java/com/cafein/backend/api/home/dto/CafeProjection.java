
package com.cafein.backend.api.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface CafeProjection {

	@Schema(description = "카페 이름", example = "5to7", required = true)
	String getName();
	String getPhoneNumber();
	String getAddress();
	String getCommentReviewCount();
	String getStatus();
	String getAverageCongestion();
}
