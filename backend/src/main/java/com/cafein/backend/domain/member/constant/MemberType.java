package com.cafein.backend.domain.member.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MemberType {

	KAKAO, GOOGLE;

	public static MemberType from(String type) {
		return MemberType.valueOf(type.toUpperCase());
	}

	public static boolean isMemberType(String type) {
		List<MemberType> memberTypes = Arrays.stream(MemberType.values())
			.filter(memberType -> memberType.name().equals(type))
			.collect(Collectors.toList());
		return memberTypes.size() != 0;
	}
}
