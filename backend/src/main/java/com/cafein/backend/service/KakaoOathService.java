package com.cafein.backend.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.cafein.backend.domain.KaKaoUserInfo;
import com.cafein.backend.domain.Oauth2UserInfo;
import com.cafein.backend.domain.User;
import com.cafein.backend.repository.UserRepository;
import com.cafein.backend.service.dto.LoginResponseDTO;
import com.cafein.backend.service.dto.OauthTokenResponseDTO;
import com.cafein.backend.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoOathService {

	private static final String BEARER_TYPE = "Bearer";

	private final InMemoryClientRegistrationRepository inMemoryRepository;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public LoginResponseDTO login(final String providerName, final String code) {
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
		OauthTokenResponseDTO tokenResponseDTO = getToken(code, provider);
		User user = getUserProfile(tokenResponseDTO, provider);

		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(user.getId()));
		String refreshToken = jwtTokenProvider.createRefreshToken();

		return createLoginResponseDTO(user, accessToken, refreshToken);
	}

	private OauthTokenResponseDTO getToken(String code, ClientRegistration provider) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(requestToken(code, provider))
			.retrieve()
			.bodyToMono(OauthTokenResponseDTO.class)
			.block();
	}

	private MultiValueMap<String, String> requestToken(final String code, final ClientRegistration provider) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUri());
		formData.add("client_secret", provider.getClientSecret());
		formData.add("client_id", provider.getClientId());
		return formData;
	}

	@Transactional
	public User getUserProfile(OauthTokenResponseDTO tokenResponseDTO, ClientRegistration provider) {
		Map<String, Object> userAttributes = getUserAttributes(provider, tokenResponseDTO);
		Oauth2UserInfo oauth2UserInfo = new KaKaoUserInfo(userAttributes);

		Optional<User> findUser = userRepository.findByEmail(oauth2UserInfo.getEmail());
		return findUser.orElseGet(() -> createNewMember(oauth2UserInfo));
	}

	private Map<String, Object> getUserAttributes(ClientRegistration provider, OauthTokenResponseDTO tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private User createNewMember(Oauth2UserInfo oauth2UserInfo) {
		User user = User.builder()
			.email(oauth2UserInfo.getEmail())
			.name(oauth2UserInfo.getNickName())
			.gender(oauth2UserInfo.getGender())
			.provider(oauth2UserInfo.getProvider())
			.build();
		userRepository.save(user);
		return user;
	}

	private static LoginResponseDTO createLoginResponseDTO(final User user, final String accessToken,
		final String refreshToken) {
		return LoginResponseDTO.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.gender(user.getGender())
			.tokenType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
