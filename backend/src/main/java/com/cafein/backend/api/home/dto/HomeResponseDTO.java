package com.cafein.backend.api.home.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class HomeResponseDTO {

	@Schema(description = "총 카페 수", example = "60", required = true)
	private long cafeCount;

	private List<HomeProjection> cafes;
}
