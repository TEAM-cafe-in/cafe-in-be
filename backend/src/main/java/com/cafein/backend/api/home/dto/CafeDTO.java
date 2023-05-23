package com.cafein.backend.api.home.dto;

import com.cafein.backend.domain.cafe.entity.Cafe;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CafeDTO {

	private String name;
	private String info;
	private String address;
	private Integer countComment;

	public static CafeDTO of(final Cafe cafe, final Integer countComment) {
		StringBuilder builder = new StringBuilder();
		createAddress(cafe, builder);
		return CafeDTO.builder()
			.name(cafe.getName())
			.info(cafe.getInfo())
			.address(builder.toString())
			.countComment(countComment)
			.build();
	}

	public static void createAddress(Cafe cafe, StringBuilder sb) {
		sb.append(cafe.getAddress().getSigungu());
		sb.append(" ");
		sb.append(cafe.getAddress().getRoadName());
		sb.append(" ");
		sb.append(cafe.getAddress().getHouseNumber());
	}
}
