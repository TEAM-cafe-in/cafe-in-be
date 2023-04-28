package com.cafein.backend.external.oauth.google.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserInfoResponseDTO {

	private String id;
	private String email;
	private String name;
	private String picture;
}
