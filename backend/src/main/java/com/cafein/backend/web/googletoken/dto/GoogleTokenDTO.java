package com.cafein.backend.web.googletoken.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class GoogleTokenDTO {

	@Data @Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class Request {

		private String code;
		private String client_id;
		private String client_secret;
		private String grant_type;
		private String redirect_uri;
	}

	@Data @Builder
	@NoArgsConstructor @AllArgsConstructor
	public static class Response {

		private String access_token;
		private Integer expires_in;
		private String scope;
		private String token_type;
		private String refresh_token;
	}
}
