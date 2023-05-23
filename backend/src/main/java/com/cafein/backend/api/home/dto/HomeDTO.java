package com.cafein.backend.api.home.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

public class HomeDTO {

	@Getter
	public static class Request {

		@NotNull
		private String local;

		@NotNull
		private String pageNo;

		@NotNull
		private String sortBy;

		@Email
		private String memberEmail;
	}

	@Getter @Builder
	public static class Response {

		private List<CafeDTO> cafes;
		private List<String> viewedCafesName;
		private Integer countCafe;
		private Integer coffeeBean;
	}
}
