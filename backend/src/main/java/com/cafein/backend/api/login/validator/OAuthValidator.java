package com.cafein.backend.api.login.validator;

import org.springframework.stereotype.Service;

import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;
import com.cafein.backend.domain.member.constant.MemberType;

@Service
public class OAuthValidator {


	public void validateMemberType(String memberType) {
		if (!MemberType.isMemberType(memberType)) {
			throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
		}
	}
}
