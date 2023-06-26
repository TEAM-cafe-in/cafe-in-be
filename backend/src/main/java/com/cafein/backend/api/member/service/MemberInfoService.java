package com.cafein.backend.api.member.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberInfoService {

	private final MemberService memberService;
	private final ViewedCafeService viewedCafeService;

	@Transactional(readOnly = true)
	public MemberInfoResponseDTO getMemberInfo(final Long memberId) {
		Member member = memberService.findMemberByMemberId(memberId);
		List<Long> viewedCafeIds = viewedCafeService.findViewedCafes(memberId);
		return MemberInfoResponseDTO.of(member, viewedCafeIds);
	}
}
