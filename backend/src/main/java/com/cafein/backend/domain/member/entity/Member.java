package com.cafein.backend.domain.member.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.cafein.backend.global.util.DateTimeUtils;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

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
	private String name;

	@Column(length = 200)
	private String profile;

	@ColumnDefault("100") // TODO 커피콩 초깃값 설정 필요
	private Integer coffeeBean;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Role role;

	@Column(length = 250)
	private String refreshToken;

	private LocalDateTime tokenExpirationTime;

	@Builder
	public Member(Long memberId, MemberType memberType, String email, String password, String name, String profile,
		Integer coffeeBean, Role role, String refreshToken, LocalDateTime tokenExpirationTime) {
		this.memberId = memberId;
		this.memberType = memberType;
		this.email = email;
		this.password = password;
		this.name = name;
		this.profile = profile;
		this.coffeeBean = coffeeBean;
		this.role = role;
		this.refreshToken = refreshToken;
		this.tokenExpirationTime = tokenExpirationTime;
	}

	@Builder
	public Member(MemberType memberType, String email, String password, String name, String profile, Role role) {
		this.memberType = memberType;
		this.email = email;
		this.password = password;
		this.name = name;
		this.profile = profile;
		this.role = role;
	}

	public void updateRefreshToken(final JwtTokenDTO jwtTokenDto) {
		this.refreshToken = jwtTokenDto.getRefreshToken();
		this.tokenExpirationTime = DateTimeUtils.convertDateToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
	}

	public void expireRefreshToken(final LocalDateTime now) {
		this.tokenExpirationTime = now;
	}
}
