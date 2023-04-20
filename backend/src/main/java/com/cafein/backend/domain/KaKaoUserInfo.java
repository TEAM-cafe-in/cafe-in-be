package com.cafein.backend.domain;

import java.util.Map;

public class KaKaoUserInfo implements Oauth2UserInfo {

	private final Map<String, Object> attributes;

	public KaKaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public Provider getProvider() {
		return Provider.KAKAO;
	}

	@Override
	public String getEmail() {
		return (String) getKakaoAccount().get("email");
	}

	@Override
	public String getNickName() {
		return (String) getProfile().get("nickname");
	}

	@Override
	public Gender getGender() {
		String gender = (String) getKakaoAccount().get("gender");

		if (gender.equals("male")) {
			return Gender.MALE;
		} else {
			return Gender.FEMALE;
		}
	}

	@Override
	public Map<String, Object> getKakaoAccount(){
		return(Map<String, Object>) attributes.get("kakao_account");
	}

	@Override
	public Map<String, Object> getProfile(){
		return (Map<String, Object>) getKakaoAccount().get("profile");
	}
}
