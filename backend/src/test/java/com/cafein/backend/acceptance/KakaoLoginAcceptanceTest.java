package com.cafein.backend.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.external.oauth.model.OAuthAttributes;
import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.cafein.backend.global.jwt.service.TokenManager;

import io.jsonwebtoken.Claims;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

class KakaoLoginAcceptanceTest extends ApiTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private TokenManager tokenManager;

	@Test
	void 로그인화면을_호출한다() {
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.when()
			.get("/login")
			.then()
			.log().all()
			.extract();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	void 첫_로그인시_회원가입을_진행한다() {
		OAuthAttributes userInfo = OAuthAttributes.builder()
			.email("test@test.com")
			.name("test")
			.profile("https://test.com")
			.memberType(MemberType.KAKAO)
			.build();
		Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());
		if (optionalMember.isEmpty()) {        //신규 회원가입
			Member oauthMember = userInfo.toMemberEntity(MemberType.KAKAO, Role.USER);
			memberService.registerMember(oauthMember);
		}

		final Member findMember = memberService.findMemberByEmail("test@test.com")
			.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

		assertAll(
			() -> assertThat(findMember.getEmail()).isEqualTo("test@test.com"),
			() -> assertThat(findMember.getName()).isEqualTo("test"),
			() -> assertThat(findMember.getProfile()).isEqualTo("https://test.com"),
			() -> assertThat(findMember.getMemberType()).isEqualTo(MemberType.KAKAO)
		);
	}

	@Test
	void 토근_발급을_테스트한다() {
		final JwtTokenDTO jwtTokenDto = tokenManager.createJwtTokenDto(1L, Role.USER);
		final Claims tokenClaims = tokenManager.getTokenClaims(jwtTokenDto.getAccessToken());
		assertThat(tokenClaims.get("memberId")).isEqualTo(1);
		assertThat(tokenClaims.get("role")).isEqualTo(Role.USER.name());
	}
}
