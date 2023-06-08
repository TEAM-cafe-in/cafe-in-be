package com.cafein.backend.api.home.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class HomeRequestDTO {

	@NotNull
	private String local;
}
