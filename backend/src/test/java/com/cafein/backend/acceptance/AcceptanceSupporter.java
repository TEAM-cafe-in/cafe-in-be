package com.cafein.backend.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import com.cafein.backend.support.utils.AcceptanceTest;
import com.cafein.backend.support.utils.DataBaseSupporter;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@AcceptanceTest
public class AcceptanceSupporter extends DataBaseSupporter {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
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
}
