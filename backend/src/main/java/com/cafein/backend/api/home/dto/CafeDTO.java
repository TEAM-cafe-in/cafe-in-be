package com.cafein.backend.api.home.dto;

import com.cafein.backend.domain.cafe.entity.Cafe;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CafeDTO {

	private String name;
	private String info;
	private String address;

	public static CafeDTO of(final Cafe cafe) {
		StringBuilder builder = new StringBuilder();
		createAddress(cafe, builder);
		return CafeDTO.builder()
			.name(cafe.getName())
			.info(cafe.getInfo())
			.address(builder.toString())
			.build();
	}

	private static void createAddress(Cafe cafe, StringBuilder sb) {
		sb.append(cafe.getAddress().getSido());
		sb.append(cafe.getAddress().getSigungu());
		sb.append(cafe.getAddress().getRoadName());
		sb.append(cafe.getAddress().getHouseNumber());
	}
}
