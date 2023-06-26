package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cafein.backend.api.member.controller.MemberController;
import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.api.member.service.MyPageService;

class MemberControllerTest extends ControllerTestSupporter {

	@Mock
	private MemberInfoService memberInfoService;

	@Mock
	private MyPageService myPageService;

	@Test
	void 회원_정보를_가져온다() throws Exception {
		given(memberInfoService.getMemberInfo(any()))
			.willReturn(MEMBER_INFO_RESPONSE_DTO);

		mockMvc(new MemberController(memberInfoService, myPageService))
			.perform(MockMvcRequestBuilders
				.get("/api/member/info"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.memberName").value("홍길동"))
			.andExpect(jsonPath("$.email").value("test@test.com"));
	}

	@Test
	void 마이페이지에_사용하는_회원_정보를_반환한다() throws Exception {
		MyPageDTO response = MyPageDTO.builder()
			.reviewCount(5L)
			.build();

		given(myPageService.getMyPageDTO(any())).willReturn(response);

		mockMvc(new MemberController(memberInfoService, myPageService))
			.perform(MockMvcRequestBuilders
				.get("/api/member/mypage"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.reviewCount").value(5L));
	}
}
