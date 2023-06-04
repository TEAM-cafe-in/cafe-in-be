package com.cafein.backend.api.home.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HomeDTO {

	@Getter
	public static class Request {

		@NotNull
		private String local;

		private String endIdx;
	}
}
