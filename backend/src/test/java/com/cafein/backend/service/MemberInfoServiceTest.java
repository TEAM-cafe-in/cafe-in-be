package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.util.ReflectionTestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.support.fixture.MemberFixture;
import com.cafein.backend.support.utils.DataBaseSupporter;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MemberInfoServiceTest extends DataBaseSupporter {

	@MockBean
	private MemberService memberService;

	@Autowired
	private MemberInfoService memberInfoService;

	@Test
	void 회원_정보를_가져온다() {
		Member createMember = MemberFixture.createMember();
		setField(createMember, "memberId", 1L);
		given(memberService.findMemberByMemberId(anyLong()))
			.willReturn(createMember);

		MemberInfoResponseDTO findMember = memberInfoService.getMemberInfo(createMember.getMemberId());

		then(memberService).should(times(1)).findMemberByMemberId(anyLong());
		assertThat(findMember.getMemberId()).isNotNull();
	}
}
