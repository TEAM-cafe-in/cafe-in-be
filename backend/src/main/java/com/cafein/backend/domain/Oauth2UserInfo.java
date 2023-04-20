package com.cafein.backend.domain;

import java.util.Map;

public interface Oauth2UserInfo {

	String getProviderId();
	Provider getProvider();
	String getEmail();
	String getNickName();
	Gender getGender();
	Map<String, Object> getKakaoAccount();
	Map<String, Object> getProfile();
}
