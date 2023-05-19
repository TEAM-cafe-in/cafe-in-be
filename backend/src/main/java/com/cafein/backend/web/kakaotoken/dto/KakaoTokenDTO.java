package com.cafein.backend.web.kakaotoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class KakaoTokenDTO {

	@Getter @Builder
	public static class Request {

		private String grant_type;
		private String client_id;
		private String redirect_uri;
		private String code;
		private String client_secret;
	}

	@Getter @Builder
	@ToString
	public static class Response {

		private String token_type;
		private String access_token;
		private Integer expires_in;
		private String refresh_token;
		private Integer refresh_token_expires_in;
		private String scope;
	}
}
