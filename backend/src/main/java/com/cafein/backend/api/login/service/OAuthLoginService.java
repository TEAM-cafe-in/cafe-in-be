package com.cafein.backend.api.login.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.login.dto.OAuthLoginDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.external.oauth.model.OAuthAttributes;
import com.cafein.backend.external.oauth.service.SocialLoginApiService;
import com.cafein.backend.external.oauth.service.SocialLoginApiServiceFactory;
import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.cafein.backend.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuthLoginService {

	private final MemberService memberService;
	private final TokenManager tokenManager;

	public OAuthLoginDTO.OAuthLoginResponse oauthLogin(String accessToken, MemberType memberType) {
		SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(memberType);
		OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
		log.info("userInfo : {}", userInfo);

		JwtTokenDTO jwtTokenDTO;
		Optional<Member> optionalMember = memberService.findMemberByEmailAndMemberType(userInfo.getEmail(), userInfo.getMemberType());
		if (optionalMember.isEmpty()) {		//신규 회원가입
			Member oauthMember = userInfo.toMemberEntity(memberType, Role.USER);
			oauthMember = memberService.registerMember(oauthMember);

			//토큰 생성
			jwtTokenDTO = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
			oauthMember.updateRefreshToken(jwtTokenDTO);
		} else {	//기존 회원
			Member oauthMember = optionalMember.get();

			//토큰 생성
			jwtTokenDTO = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
			oauthMember.updateRefreshToken(jwtTokenDTO);
		}

		return OAuthLoginDTO.OAuthLoginResponse.of(jwtTokenDTO);
	}
}
