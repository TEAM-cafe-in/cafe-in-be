package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.CafeFixture.*;
import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.api.member.service.MemberInfoService;
import com.cafein.backend.domain.member.repository.MemberRepository;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.viewedcafe.repository.ViewedCafeRepository;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;
import com.cafein.backend.support.fixture.CafeFixture;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MemberInfoServiceTest {

	@InjectMocks
	private MemberInfoService memberInfoService;

	@Mock
	private MemberService memberService;

	@Mock
	private ViewedCafeService viewedCafeService;

	@Test
	void 회원_정보를_가져온다() {
		given(memberService.findMemberByMemberId(anyLong())).willReturn(MEMBER);
		given(viewedCafeService.findViewedCafes(anyLong())).willReturn(VIEWED_CAFE_IDS);

		memberInfoService.getMemberInfo(MEMBER.getMemberId());

		then(memberService).should(times(1)).findMemberByMemberId(anyLong());
		then(viewedCafeService).should(times(1)).findViewedCafes(anyLong());
	}
}
