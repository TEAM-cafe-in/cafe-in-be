package com.cafein.backend.global.resolver;

import com.cafein.backend.domain.member.constant.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDTO {

	private Long memberId;
	private Role role;
}
