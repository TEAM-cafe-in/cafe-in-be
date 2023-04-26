package com.cafein.backend.external.oauth.kakao.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cafein.backend.external.oauth.kakao.client.KakaoUserInfoClient;
import com.cafein.backend.external.oauth.model.OAuthAttributes;
import com.cafein.backend.external.oauth.service.SocialLoginApiService;
import com.cafein.backend.global.jwt.constant.GrantType;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.external.oauth.kakao.dto.KakaoUserInfoResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

	private final KakaoUserInfoClient kakaoUserInfoClient;
	private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf8;";

	@Override
	public OAuthAttributes getUserInfo(final String accessToken) {
		KakaoUserInfoResponseDTO kakaoUserInfoResponseDTO = kakaoUserInfoClient.getKakaoUserInfo(CONTENT_TYPE,
			GrantType.BEARER.getType() + " " + accessToken);
		KakaoUserInfoResponseDTO.KakaoAccount kakaoAccount = kakaoUserInfoResponseDTO.getKakaoAccount();
		String email = kakaoAccount.getEmail();

		return OAuthAttributes.builder()
			.email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDTO.getId() : email)
			.name(kakaoAccount.getProfile().getNickname())
			.profile(kakaoAccount.getProfile().getThumbnailImageUrl())
			.memberType(MemberType.KAKAO)
			.build();
	}
}
