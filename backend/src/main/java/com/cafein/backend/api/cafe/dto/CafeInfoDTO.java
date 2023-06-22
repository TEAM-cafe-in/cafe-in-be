package com.cafein.backend.api.cafe.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CafeInfoDTO {

	private CafeInfoProjection cafeInfoProjection;

	@Schema(name = "comment", description = "카페 리뷰", type = "array", example = "[여기 카페 너무 트렌디해요!, 사장님이 잘생겼어요!]", required = true)
	private List<String> comments;
}
