package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cafein.backend.global.error.GlobalExceptionHandler;
import com.cafein.backend.global.resolver.MemberInfoArgumentResolver;

@ExtendWith(MockitoExtension.class)
public abstract class ControllerTestSupporter {

	@Mock
	private MemberInfoArgumentResolver memberInfoArgumentResolver;

	@BeforeEach
	void setUp() throws Exception {
		lenient().when(memberInfoArgumentResolver.supportsParameter(any()))
			.thenReturn(true);

		lenient().when(memberInfoArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.thenReturn(MEMBER_INFO_DTO);
	}

	protected MockMvc mockMvc(Object controller) {
		return MockMvcBuilders.standaloneSetup(controller)
			.setControllerAdvice(new GlobalExceptionHandler())
			.setCustomArgumentResolvers(memberInfoArgumentResolver)
			.alwaysDo(MockMvcResultHandlers.print())
			.build();
	}
}
