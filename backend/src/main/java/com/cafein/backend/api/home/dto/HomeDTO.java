package com.cafein.backend.api.home.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class HomeDTO {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		private String local;
		private String pageNo;
		private String sortBy;
		private String memberEmail;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private List<CafeDTO> cafeList;
		private Integer coffeeBean;
	}
}
