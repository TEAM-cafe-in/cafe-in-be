package com.cafein.backend.global.resolver;

import com.cafein.backend.domain.member.constant.Role;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class MemberInfoDTO {

	private Long memberId;
	private Role role;
}
