package com.cafein.web.kakaotoken.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.cafein.backend.acceptance.ApiTest;
import com.cafein.backend.api.token.dto.AccessTokenResponseDTO;
import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;


@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KakaoTokenControllerTest extends ApiTest {

	private static final String KAKAO_ACCESS_TOKEN = "woPPn2hsXhlcICfKVw4IPYEWij00MlHD7oeboATXCj10mQAAAYf_dH_K";
	private static final String EMAIL = "uichan293@naver.com";
	private String CAFEIN_ACCESS_TOKEN = "";
	private final Map<String, String> paramBody = new HashMap<>();

	@Autowired
	private MemberService memberService;

	@Autowired
	private TokenService tokenService;

	@BeforeEach
	void init() {
		paramBody.put("memberType", "KAKAO");
	}

	@Order(1)
	@DisplayName("access token을 이용해 login을 테스트한다")
	@Test
	void login_with_access_token() {
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.auth().oauth2(KAKAO_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(paramBody)
			.when()
			.post("/api/oauth/login")
			.then().log().all()
			.extract();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Order(2)
	@DisplayName("회원 가입을 테스트한다.")
	@Test
	void singUp_member() {
		Member member = memberService.findMemberByEmail(EMAIL)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));

		assertThat(member.getEmail()).isEqualTo(EMAIL);
	}

	@Order(3)
	@DisplayName("access token 재발급을 테스트한다.")
	@Test
	void update_access_token() {
		Member member = memberService.findMemberByEmail(EMAIL)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));

		String refreshToken = member.getRefreshToken();
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.auth().oauth2(refreshToken)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/api/access-token/issue")
			.then().log().all()
			.extract();

		AccessTokenResponseDTO accessTokenDto = tokenService.createAccessTokenByRefreshToken(refreshToken);
		CAFEIN_ACCESS_TOKEN = accessTokenDto.getAccessToken();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@Order(4)
	@DisplayName("로그아웃을 테스트한다.")
	@Test
	void logout_member() {
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.auth().oauth2(CAFEIN_ACCESS_TOKEN)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/api/logout")
			.then().log().all()
			.extract();

		assertThat(response.body().asString()).isEqualTo("logout success");
	}
}
