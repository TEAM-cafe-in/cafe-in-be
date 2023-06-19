package com.cafein.backend.api.login.validator;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

public class OAuthValidator {

	public static void validateMemberType(String memberType) {
		if (!MemberType.isMemberType(memberType)) {
			throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
		}
	}
}
