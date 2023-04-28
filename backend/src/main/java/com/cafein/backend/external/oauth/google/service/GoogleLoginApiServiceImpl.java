package com.cafein.backend.external.oauth.google.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.external.oauth.google.client.GoogleUserInfoClient;
import com.cafein.backend.external.oauth.google.dto.GoogleUserInfoResponseDTO;
import com.cafein.backend.external.oauth.model.OAuthAttributes;
import com.cafein.backend.external.oauth.service.SocialLoginApiService;
import com.cafein.backend.global.jwt.constant.GrantType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginApiServiceImpl implements SocialLoginApiService {

	private final GoogleUserInfoClient googleUserInfoClient;

	@Override
	public OAuthAttributes getUserInfo(final String accessToken) {
		GoogleUserInfoResponseDTO googleUserInfoResponseDTO = googleUserInfoClient
			.getGoogleUserInfo(GrantType.BEARER.getType() + " " + accessToken);

		return OAuthAttributes.builder()
			.email(!StringUtils.hasText(googleUserInfoResponseDTO.getEmail()) ?
				googleUserInfoResponseDTO.getId() : googleUserInfoResponseDTO.getEmail())
			.name(googleUserInfoResponseDTO.getName())
			.profile(googleUserInfoResponseDTO.getPicture())
			.memberType(MemberType.GOOGLE)
			.build();
	}
}
