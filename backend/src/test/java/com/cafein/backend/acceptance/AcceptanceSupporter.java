package com.cafein.backend.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.cafein.backend.support.utils.AcceptanceTest;
import com.cafein.backend.support.utils.DataBaseSupporter;

import io.restassured.RestAssured;

@AcceptanceTest
public class AcceptanceSupporter extends DataBaseSupporter {

	@LocalServerPort
	private int port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}
}
