package com.cafein.backend.service.dto;

import com.cafein.backend.domain.Gender;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {

	private Long id;
	private String name;
	private String email;
	private Gender gender;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
}
