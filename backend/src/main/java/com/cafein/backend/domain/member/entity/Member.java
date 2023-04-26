package com.cafein.backend.domain.member.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cafein.backend.domain.common.BaseEntity;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.global.util.DateTimeUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private MemberType memberType;

	@Column(unique = true, length = 50, nullable = false)
	private String email;

	@Column(length = 200)
	private String password;

	@Column(nullable = false, length = 20)
	private String memberName;

	@Column(length = 200)
	private String profile;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Role role;

	@Column(length = 250)
	private String refreshToken;

	private LocalDateTime tokenExpirationTime;

	@Builder
	public Member(MemberType memberType, String email, String password, String memberName, String profile, Role role) {
		this.memberType = memberType;
		this.email = email;
		this.password = password;
		this.memberName = memberName;
		this.profile = profile;
		this.role = role;
	}

	public void updateRefreshToken(final JwtTokenDTO jwtTokenDto) {
		this.refreshToken = jwtTokenDto.getRefreshToken();
		this.tokenExpirationTime = DateTimeUtils.convertToLoalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
	}

	public void expireRefreshToken(final LocalDateTime now) {
		this.tokenExpirationTime = now;
	}
}
