package com.cafein.backend.integration;

import static com.cafein.backend.support.fixture.LoginFixture.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.token.service.TokenService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.support.utils.DataBaseSupporter;
import com.cafein.backend.support.utils.IntegrationTest;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@IntegrationTest
public class IntegrationSupporter extends DataBaseSupporter {

	@LocalServerPort
	private int port;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MemberService memberService;

	protected Member member;
	protected String access_token;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		member = memberService.findMemberByMemberId(1L);
		access_token = tokenService.createAccessTokenByRefreshToken(member.getRefreshToken()).getAccessToken();
	}

	protected ExtractableResponse<Response> post(final String uri, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(body)
			.when().post(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> post(final String uri, final Header header, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.body(body)
			.when().post(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> get(final String uri) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().get(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> get(final String uri, final Header header) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.when().get(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> put(final String uri, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(body)
			.when().put(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> put(final String uri, final Header header, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.body(body)
			.when().put(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> patch(final String uri, final Header header) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.when().patch(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> patch(final String uri, final Header header, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.body(body)
			.when().patch(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> delete(final String uri, final Header header) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.when().delete(uri)
			.then().log().all()
			.extract();
	}

	protected ExtractableResponse<Response> delete(final String uri, final Header header, final Object body) {
		return RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.header(header)
			.body(body)
			.when().delete(uri)
			.then().log().all()
			.extract();
	}

	protected Header generateAccessHeader() {
		return new Header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_ACCESS);
	}

	protected Header generateAccessHeader(String accessToken) {
		return new Header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
	}

	protected Header generateRefreshHeader() {
		return new Header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_REFRESH);
	}
}
