package com.cafein.backend.api.member.dto;

import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

	public static MemberInfoResponseDTO of(final Member member) {
		return MemberInfoResponseDTO.builder()
			.memberId(member.getMemberId())
			.memberName(member.getName())
			.email(member.getEmail())
			.profile(member.getProfile())
			.role(member.getRole())
			.build();
	}
}
