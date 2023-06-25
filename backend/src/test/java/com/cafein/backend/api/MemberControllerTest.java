package com.cafein.backend.api;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cafein.backend.api.member.controller.MemberController;
import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.api.member.service.MyPageService;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

	@Mock
	private MemberInfoService memberInfoService;

	@Mock
	private MyPageService myPageService;

	@InjectMocks
	private MemberController memberController;

	@Test
	void 회원_정보를_가져온다() {
		given(memberInfoService.getMemberInfo(any()))
			.willReturn(MEMBER_INFO_RESPONSE_DTO);

		final ResponseEntity<MemberInfoResponseDTO> result = memberController.getMemberInfo(MEMBER_INFO_DTO);

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isEqualTo(MEMBER_INFO_RESPONSE_DTO);
	}

	@Test
	void 마이페이지에_사용하는_회원_정보를_반환한다() {
		MyPageDTO response = MyPageDTO.builder()
			.reviewCount(5L)
			.build();
		given(myPageService.getMyPageDTO(1L)).willReturn(response);

		final ResponseEntity<MyPageDTO> result = memberController.getMyPage(MEMBER_INFO_DTO);

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody()).isEqualTo(response);
	}
}
