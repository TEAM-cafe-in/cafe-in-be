package com.cafein.backend.external.oauth.service;

import com.cafein.backend.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

	OAuthAttributes getUserInfo(String accessToken);
}
