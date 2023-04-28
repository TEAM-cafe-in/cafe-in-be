package com.cafein.backend.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoResponseDTO {

	private String id;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class KakaoAccount {

		private String email;
		private Profile profile;

		@Data
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Profile {

			private String nickname;

			@JsonProperty("thumbnail_image_url")
			private String thumbnailImageUrl;
		}
	}
}
