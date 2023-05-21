package com.cafein.backend.support.fixture;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.global.resolver.MemberInfoDTO;

public class MemberFixture {

	public static String member = "{\"memberId\" : \"1L\", \"role\" : \"ADMIN\"}";

	public static Member createMember() {
		return Member.builder()
			.memberType(MemberType.KAKAO)
			.name("홍길동")
			.email("test@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.build();
	}

	public static MemberInfoDTO memberInfoDTO() {
		return createMemberInfoDTO();
	}

	private static MemberInfoDTO createMemberInfoDTO() {
		return MemberInfoDTO.builder()
			.memberId(1L)
			.role(Role.USER)
			.build();
	}

	public static MemberInfoResponseDTO memberInfoResponseDTO() {
		return createMemberInfoResponse();
	}

	private static MemberInfoResponseDTO createMemberInfoResponse() {
		return MemberInfoResponseDTO.builder()
			.memberId(1L)
			.memberName("홍길동")
			.email("test@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.role(Role.USER)
			.build();
	}
}
