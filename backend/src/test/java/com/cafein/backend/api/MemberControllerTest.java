package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.cafein.backend.api.member.controller.MemberController;
import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.api.member.service.MyPageService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.support.fixture.LoginFixture;

class MemberControllerTest extends ControllerTestSupporter {

	@Mock
	private MemberService memberService;

	@Mock
	private MemberInfoService memberInfoService;

	@Mock
	private MyPageService myPageService;

	@Test
	void 회원_정보를_가져온다() throws Exception {
		given(memberInfoService.getMemberInfo(anyLong()))
			.willReturn(MEMBER_INFO_RESPONSE_DTO);

		mockMvc(new MemberController(memberService, memberInfoService, myPageService))
			.perform(get("/api/member/info"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.memberName").value("홍길동"))
			.andExpect(jsonPath("$.email").value("test@test.com"));
	}

	@Test
	void 마이페이지에_사용하는_회원_정보를_반환한다() throws Exception {
		MyPageDTO response = MyPageDTO.builder()
			.reviewCount(5L)
			.build();

		given(myPageService.getMyPageDTO(anyLong())).willReturn(response);

		mockMvc(new MemberController(memberService, memberInfoService, myPageService))
			.perform(get("/api/member/mypage"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.reviewCount").value(5L));
	}

	@Test
	void 회원_이름을_수정한다() throws Exception {
		mockMvc(new MemberController(memberService, memberInfoService, myPageService))
			.perform(patch("/api/member/name")
				.content("{\"name\": \"손흥민\"}")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.AUTHORIZATION, LoginFixture.AUTHORIZATION_HEADER_ACCESS)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value("Name change successful!"));

		then(memberService).should(times(1)).updateMemberName(anyLong(), eq("손흥민"));
	}
}
