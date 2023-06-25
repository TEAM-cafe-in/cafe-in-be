package com.cafein.backend.support.fixture;

import static com.cafein.backend.support.fixture.LoginFixture.*;

import java.time.LocalDateTime;

import com.cafein.backend.api.member.dto.MemberInfoResponseDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.global.resolver.MemberInfoDTO;

public class MemberFixture {

	public static final Member MEMBER = createMember();
	public static final Member MEMBER_WITH_EXPIRED_REFRESH_TOKEN = createMemberWithExpiredRefreshToken();
	public static final MemberInfoDTO MEMBER_INFO_DTO = memberInfoDTO();
	public static final MemberInfoResponseDTO MEMBER_INFO_RESPONSE_DTO = memberInfoResponseDTO();

	private static Member createMember() {
		return Member.builder()
			.memberId(1L)
			.memberType(MemberType.KAKAO)
			.name("황의찬")
			.email("chan@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.coffeeBean(100)
			.refreshToken(REFRESH_TOKEN)
			.tokenExpirationTime(LocalDateTime.now().plusDays(14))
			.build();
	}

	private static Member createMemberWithExpiredRefreshToken() {
		return Member.builder()
			.memberId(1L)
			.memberType(MemberType.KAKAO)
			.name("황의찬")
			.email("chan@test.com")
			.profile("http://k.kakaocdn.net/img_110x110.jpg")
			.coffeeBean(100)
			.refreshToken(REFRESH_TOKEN)
			.tokenExpirationTime(LocalDateTime.now().minusDays(1))
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
