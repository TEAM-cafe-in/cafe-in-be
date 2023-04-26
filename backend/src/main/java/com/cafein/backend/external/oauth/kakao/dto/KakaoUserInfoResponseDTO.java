package com.cafein.backend.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class KakaoUserInfoResponseDTO {

	private String id;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Data
	public static class KakaoAccount {
		private String email;
		private Profile profile;

		@Data
		public static class Profile {

			private String nickname;

			@JsonProperty("thumbnail_image_url")
			private String thumbnailImageUrl;
		}
	}
}
