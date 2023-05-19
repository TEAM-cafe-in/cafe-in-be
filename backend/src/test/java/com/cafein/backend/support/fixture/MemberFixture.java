package com.cafein.backend.support.fixture;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;

public class MemberFixture {

	public static Member member = createMember();

	private static Member createMember() {
		return Member.builder()
			.memberType(MemberType.KAKAO)
			.email("test@test.com")
			.name("test")
			.password("test")
			.profile("https://test.com")
			.role(Role.USER)
			.build();
	}
}
