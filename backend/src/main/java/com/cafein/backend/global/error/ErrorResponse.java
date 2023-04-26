package com.cafein.backend.global.error;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private String errorCode;
	private String errorMessage;

	public static ErrorResponse of(String errorCode, String errorMessage) {
		return ErrorResponse.builder()
			.errorCode(errorCode)
			.errorMessage(errorMessage)
			.build();
	}

	public static ErrorResponse of(String errorCode, BindingResult bindingResult) {
		return ErrorResponse.builder()
			.errorCode(errorCode)
			.errorMessage(createErrorMessage(bindingResult))
			.build();
	}

	private static String createErrorMessage(final BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			if (!isFirst) {
				sb.append(", ");
			} else {
				isFirst = false;
			}
			sb.append("[");
			sb.append(fieldError.getField());
			sb.append("]");
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
}
