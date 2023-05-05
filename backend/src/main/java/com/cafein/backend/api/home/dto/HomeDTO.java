package com.cafein.backend.api.home.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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

		@NotNull
		private String local;

		@NotNull
		private String pageNo;

		@NotNull
		private String sortBy;

		@Email
		private String memberEmail;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private List<CafeDTO> cafes;
		private Integer coffeeBean;
	}
}
