package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.support.utils.DataBaseSupporter;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MemberInfoServiceTest extends DataBaseSupporter {

	@Autowired
	private MemberInfoService memberInfoService;

	@MockBean
	private MemberService memberService;

	@Test
	void 회원_정보를_가져온다() {
		given(memberService.findMemberByMemberId(anyLong())).willReturn(MEMBER);

		MemberInfoResponseDTO memberInfoResponseDTO = memberInfoService.getMemberInfo(MEMBER.getMemberId());

		then(memberService).should(times(1)).findMemberByMemberId(anyLong());
		assertThat(memberInfoResponseDTO.getMemberId()).isNotNull();
	}
}
