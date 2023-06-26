package com.cafein.backend.api.member.dto;

import java.util.List;

import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class MemberInfoResponseDTO {

	@Schema(description = "회원 아이디", example = "1", required = true)
	private Long memberId;

	@Schema(description = "이메일", example = "test@gmail.com", required = true)
	private String email;

	@Schema(description = "회원 이름", example = "홍길동", required = true)
	private String memberName;

	@Schema(description = "프로필 이미지 경로", example = "http://k.kakaocdn.net/img_110x110.jpg", required = false)
	private String profile;

	@Schema(description = "회원의 역할", example = "USER", required = true)
	private Role role;

	@Schema(description = "회원이 조회한 카페 Id", example = "[1, 2, 3]", required = true)
	private List<Long> viewedCafeIds;

	public static MemberInfoResponseDTO of(final Member member, final List<Long> viewedCafeIds) {
		return MemberInfoResponseDTO.builder()
			.memberId(member.getMemberId())
			.memberName(member.getName())
			.email(member.getEmail())
			.profile(member.getProfile())
			.role(member.getRole())
			.viewedCafeIds(viewedCafeIds)
			.build();
	}

	@Override
	public String toString() {
		return "MemberInfoResponseDTO{" +
			"memberId=" + memberId +
			", email='" + email + '\'' +
			", memberName='" + memberName + '\'' +
			", profile='" + profile + '\'' +
			", role=" + role +
			", viewedCafeIds=" + viewedCafeIds +
			'}';
	}
}
