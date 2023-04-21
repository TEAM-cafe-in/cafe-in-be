package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import com.cafein.backend.domain.Gender;
import com.cafein.backend.domain.Provider;
import com.cafein.backend.domain.User;
import com.cafein.backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 0. 카카오 로그인, Id로 Authorization code 생성
 * 1. Authorization code 로 토큰 요청
 * 2. Access token 발급
 * 3. Access token으로 https://kapi.kakao.com/v2/user/me에 자원 요청, 자원 응답
 * 4. 유저 정보에 따라 JWT토큰 발급
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KakaoOathServiceTest {

	private static final String ACCESS_TOKEN = "nzlrOFMKp30aB-5DbIfWlQDFXewhVvlZAVfPWbYUCisNIAAAAYehmGLE";

	private KakaoResponseDTO kakaoResponseDTO;
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private UserRepository userRepository;

	@Order(1)
	@DisplayName("Autorization code 생성을 확인한다")
	@Test
	void kakao_authorization() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("username", "uichan293@naver.com");
		paramMap.put("password", "a87524626");
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(paramMap)
			.when()
			.get("https://accounts.kakao.com/login")
			.then().log().all()
			.extract();

		System.out.println(response.body().asString()+ "asString");

		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
	}

	@Order(2)
	@DisplayName("Access token으로 유저 정보 응답을 테스트한다.")
	@Test
	void request_resource_with_access_token() throws JsonProcessingException {
		ExtractableResponse<Response> response = RestAssured.given().log().all()
			.auth()
			.oauth2(ACCESS_TOKEN)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.when().get("https://kapi.kakao.com/v2/user/me")
			.then().log().all()
			.extract();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		kakaoResponseDTO = objectMapper.readValue(response.body().asString(), KakaoResponseDTO.class);

		assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
	}

	@Order(3)
	@DisplayName("카카오 로그인 사용자 회원 저장을 테스트한다")
	@Test
	void save_member_with_kakao_login() {
		User user = User.builder()
			.name(kakaoResponseDTO.properties.nickname)
			.email(kakaoResponseDTO.kakao_account.email)
			.gender(kakaoResponseDTO.kakao_account.gender)
			.provider(Provider.KAKAO)
			.build();

		System.out.println(user);

		userRepository.save(user);
	}


	@Getter @Setter
	@ToString
	static class KakaoResponseDTO {

		private Long id;
		private Properties properties;
		private KakaoAccount kakao_account;

		@Getter @Setter
		@ToString
		static class Properties {
			private String nickname;
		}

		@Getter @Setter
		@ToString
		static class KakaoAccount {
			private String email;
			private Gender gender;
		}
	}
}