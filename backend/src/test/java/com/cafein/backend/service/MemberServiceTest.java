package com.cafein.backend.service;

import static com.cafein.backend.support.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.repository.MemberRepository;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.global.error.exception.AuthenticationException;
import com.cafein.backend.global.error.exception.BusinessException;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	void 회원_등록을_한다() {
		given(memberRepository.save(any())).willReturn(MEMBER);
		given(memberRepository.findByEmailAndMemberType(any(), any())).willReturn(Optional.empty());

		memberService.registerMember(MEMBER);

		then(memberRepository).should(times(1)).save(any(Member.class));
	}

	@Test
	void 회원_등록시_이미_가입된_회원이면_예외를_던진다() {
		given(memberRepository.findByEmailAndMemberType(any(), any())).willReturn(Optional.of(MEMBER));

		assertThatThrownBy(() -> memberService.registerMember(MEMBER))
			.isInstanceOf(BusinessException.class)
			.hasMessage("이미 가입된 회원입니다.");
	}

	@Test
	void 이메일로_회원을_조회한다() {
		given(memberRepository.findByEmail(any())).willReturn(Optional.of(MEMBER));

		memberService.findMemberByEmail(MEMBER.getEmail());

		then(memberRepository).should(times(1)).findByEmail(anyString());
	}

	@Test
	void 이메일과_멤버타입으로_회원을_조회한다() {
		given(memberRepository.findByEmailAndMemberType(any(), any())).willReturn(Optional.of(MEMBER));

		memberService.findMemberByEmailAndMemberType(MEMBER.getEmail(), MEMBER.getMemberType());

		then(memberRepository).should(times(1)).findByEmailAndMemberType(any(), any());
	}

	@Test
	void refresh_Token으로_회원을_조회한다() {
		given(memberRepository.findByRefreshToken(any())).willReturn(Optional.of(MEMBER));

		memberService.findMemberByRefreshToken(MEMBER.getRefreshToken());

		then(memberRepository).should(times(1)).findByRefreshToken(anyString());
	}

	@Test
	void 만료된_refresh_Token으로_회원을_조회시_예외를_던진다() {
		given(memberRepository.findByRefreshToken(any())).willReturn(Optional.of(MEMBER_WITH_EXPIRED_REFRESH_TOKEN));

		assertThatThrownBy(() -> memberService.findMemberByRefreshToken(MEMBER_WITH_EXPIRED_REFRESH_TOKEN.getRefreshToken()))
			.isInstanceOf(AuthenticationException.class)
			.hasMessage("해당 Refresh Token은 만료 되었습니다.");
	}

	@Test
	void 존재하지_않는_refresh_Token으로_회원을_조회시_예외를_던진다() {
		assertThatThrownBy(() -> memberService.findMemberByRefreshToken("wrong_token"))
			.isInstanceOf(AuthenticationException.class)
			.hasMessage("해당 Refresh Token은 존재하지 않습니다.");
	}
}
