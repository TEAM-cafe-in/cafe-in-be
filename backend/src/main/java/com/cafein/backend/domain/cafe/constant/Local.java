package com.cafein.backend.domain.cafe.constant;

import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.BusinessException;

public enum Local {

	HONGDAE, YEONNAM, SEONGSU;

	public static Local from(String local) {
		try {
			return Local.valueOf(local.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BusinessException(ErrorCode.LOCAL_NOT_EXIST);
		}
	}
}
