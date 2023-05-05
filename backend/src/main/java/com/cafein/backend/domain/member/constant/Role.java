package com.cafein.backend.domain.member.constant;

public enum Role {

	USER, ADMIN;

	public static Role from(final String role) {
		return Role.valueOf(role);
	}
}
