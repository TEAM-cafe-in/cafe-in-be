package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cafein.backend.global.error.GlobalExceptionHandler;
import com.cafein.backend.global.interceptor.AuthenticationInterceptor;
import com.cafein.backend.global.resolver.MemberInfoArgumentResolver;

@ExtendWith(MockitoExtension.class)
public abstract class ControllerTestSupporter {

	protected MockMvc mockMvc(Object controller) {
		return MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}
}
