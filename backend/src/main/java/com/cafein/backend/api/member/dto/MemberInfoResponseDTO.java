package com.cafein.backend.api.member.dto;

import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponseDTO {

	private Long memberId;
	private String email;
	private String memberName;
	private String profile;
	private Role role;

	public static MemberInfoResponseDTO of(final Member member) {
		return MemberInfoResponseDTO.builder()
			.memberId(member.getMemberId())
			.memberName(member.getMemberName())
			.email(member.getEmail())
			.profile(member.getProfile())
			.role(member.getRole())
			.build();
	}
}
