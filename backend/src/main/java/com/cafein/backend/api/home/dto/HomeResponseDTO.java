package com.cafein.backend.api.home.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class HomeResponseDTO {

	private long cafeCount;

	private List<CafeProjection> cafes;
}
