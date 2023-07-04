package com.cafein.backend.domain.member.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.repository.MemberRepository;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.AuthenticationException;
import com.cafein.backend.global.error.exception.BusinessException;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member registerMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	private void validateDuplicateMember(Member member) {
		Optional<Member> optionalMember = memberRepository.findByEmailAndMemberType(member.getEmail(), member.getMemberType());
		if (optionalMember.isPresent()) {
			throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
		}
	}

	@Transactional(readOnly = true)
	public Optional<Member> findMemberByEmailAndMemberType(String email, MemberType memberType) {
		return memberRepository.findByEmailAndMemberType(email, memberType);
	}

	@Transactional(readOnly = true)
	public Optional<Member> findMemberByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Transactional(readOnly = true)
	public Member findMemberByRefreshToken(final String refreshToken) {
		Member member = memberRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
		LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();
		if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
			throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
		}
		return member;
	}

	@Transactional(readOnly = true)
	public Member findMemberByMemberId(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));
	}

	public void subtractCoffeeBean(final Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));
		if (member.getCoffeeBean() < 2) {
			throw new BusinessException(ErrorCode.NOT_ENOUGH_COFFEE_BEAN);
		}
		member.subtractCoffeeBean(member.getCoffeeBean());
	}

	@Transactional
	public void updateMemberName(Long memberId, String name) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));
		member.updateName(name);
	}
}
