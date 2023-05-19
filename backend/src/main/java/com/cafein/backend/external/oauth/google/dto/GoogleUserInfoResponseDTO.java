package com.cafein.backend.external.oauth.google.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class GoogleUserInfoResponseDTO {

	private String id;
	private String email;
	private String name;
	private String picture;
}
