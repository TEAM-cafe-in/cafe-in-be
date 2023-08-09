package com.cafein.backend.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	// 인증
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
	NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈값입니다."),
	NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
	REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 Refresh Token은 존재하지 않습니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 Refresh Token은 만료 되었습니다."),
	NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 Access Token이 아닙니다."),

	// 회원
	INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "M-001", "잘못된 회원 타입 입니다."),
	ALREADY_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "M-002", "이미 가입된 회원입니다."),
	MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M-003", "해당 회원은 존재하지 않습니다."),
	NOT_ENOUGH_COFFEE_BEAN(HttpStatus.BAD_REQUEST, "M-004", "커피 콩이 부족합니다."),

	// 카페
	CAFE_NOT_EXIST(HttpStatus.BAD_REQUEST, "C-001", "해당 카페는 존재하지 않습니다."),
	CAFE_ALREADY_VIEWED(HttpStatus.BAD_REQUEST, "C-002", "이미 조회한 카페입니다."),

	// 지역
	LOCAL_NOT_EXIST(HttpStatus.BAD_REQUEST, "L-001", "해당 지역은 존재하지 않습니다."),

	// 리뷰
	REVIEWED_CAFE_WITHIN_A_DAY(HttpStatus.BAD_REQUEST, "R-001", "해당 카페에 대해 하루에 한번만 리뷰를 작성할 수 있습니다."),
	REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "R-002", "해당 카페에는 리뷰가 존재하지 않아 혼잡도를 확인할 수 없습니다."),

	// 댓글
	KEYWORD_NOT_EXIST(HttpStatus.BAD_REQUEST,"CO-001","해당 키워드는 존재하지 않습니다."),
	COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "CO-002", "카페에 해당하는 댓글이 존재하지 않습니다."),

	// 혼잡도
	INVALID_CAFE_CONGESTION_VALUE(HttpStatus.BAD_REQUEST, "CR-001" , "카페의 혼잡도는 1[LOW], 2[MEDIUM], 3[HIGH] 중 하나입니다."),
	CONGESTION_ALREADY_REQUESTED(HttpStatus.BAD_REQUEST, "CR-002", "오늘 이미 혼잡도를 요청한 카페입니다.");

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;

	ErrorCode(final HttpStatus httpStatus, final String errorCode, final String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
