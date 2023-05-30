package com.cafein.backend.support.fixture;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.global.resolver.MemberInfoDTO;

public class MemberFixture {

	public static final Member MEMBER = createMember();
	public static final MemberInfoDTO MEMBER_INFO_DTO = memberInfoDTO();
	public static final MemberInfoResponseDTO MEMBER_INFO_RESPONSE_DTO = memberInfoResponseDTO();

	private static Member createMember() {
		return Member.builder()
			.memberType(MemberType.KAKAO)
			.name("홍길동")
			.email("test@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.build();
	}

	private static MemberInfoDTO memberInfoDTO() {
		return MemberInfoDTO.builder()
			.memberId(1L)
			.role(Role.USER)
			.build();
	}

	private static MemberInfoResponseDTO memberInfoResponseDTO() {
		return MemberInfoResponseDTO.builder()
			.memberId(1L)
			.memberName("홍길동")
			.email("test@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.role(Role.USER)
			.build();
	}
}
