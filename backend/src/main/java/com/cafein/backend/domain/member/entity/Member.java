package com.cafein.backend.domain.member.entity;

import static javax.persistence.CascadeType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.review.entity.Review;
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
@Table(indexes = {
		@Index(name = "idx_email_memberType", columnList = "email, memberType", unique = true),
		@Index(name = "idx_refresh_token", columnList = "refreshToken")
})
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private MemberType memberType;

	@Column(length = 50, nullable = false)
	private String email;

	@Column(length = 200)
	private String password;

	@Column(nullable = false, length = 20)
	private String name;

	@Column(length = 200)
	private String profile;

	@ColumnDefault("100")
	private Integer coffeeBean;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Role role;

	@Column(length = 250)
	private String refreshToken;

	private LocalDateTime tokenExpirationTime;

	@OneToMany(mappedBy = "member", cascade = ALL)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = ALL)
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Member(final Long memberId, final MemberType memberType, final String email,
		final String password, final String name, final String profile, final Integer coffeeBean,
		final Role role, final String refreshToken, final LocalDateTime tokenExpirationTime) {
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

	public void updateRefreshToken(final JwtTokenDTO jwtTokenDto) {
		this.refreshToken = jwtTokenDto.getRefreshToken();
		this.tokenExpirationTime = DateTimeUtils.convertDateToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
	}

	public void updateName(final String name) {
		this.name = name;
	}

	public void expireRefreshToken(final LocalDateTime now) {
		this.tokenExpirationTime = now;
	}

	public void subtractCoffeeBean(Integer currentCoffeeBean) {
		this.coffeeBean = currentCoffeeBean - 2;
	}

	public void addCoffeeBean(Integer currentCoffeeBean) {
		this.coffeeBean = currentCoffeeBean + 2;
	}
}
