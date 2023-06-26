package com.cafein.backend.api;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cafein.backend.global.error.GlobalExceptionHandler;

public abstract class ControllerTestSupporterV1 {

	protected MockMvc mockMvc(Object controller) {
		return MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}
}
