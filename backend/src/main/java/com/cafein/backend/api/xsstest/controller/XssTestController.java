package com.cafein.backend.api.xsstest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.xsstest.dto.XssTestDTO;

@RestController
@RequestMapping("/xss")
public class XssTestController {

	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public XssTestDTO xssTest1(XssTestDTO xssTestDTO) {
		return xssTestDTO;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public XssTestDTO xssTest2(@RequestBody XssTestDTO xssTestDTO) {
		return xssTestDTO;
	}
}
