package com.cafein.backend.global.error.exception;

import com.cafein.backend.global.error.ErrorCode;

public class AuthenticationException extends BusinessException {

	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
