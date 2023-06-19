package com.cafein.backend.external.oauth.model;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter @Builder
public class OAuthAttributes {

	private static final int COFFEE_BEAN = 100;

	private String name;
	private String email;
	private String profile;
	private MemberType memberType;

	public Member toMemberEntity(MemberType memberType, Role role) {
		return Member.builder()
			.name(name)
			.email(email)
			.memberType(memberType)
			.profile(profile)
			.role(role)
			.coffeeBean(COFFEE_BEAN)
			.build();
	}
}
