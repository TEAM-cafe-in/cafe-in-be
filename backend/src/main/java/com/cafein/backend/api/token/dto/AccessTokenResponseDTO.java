package com.cafein.backend.api.token.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AccessTokenResponseDTO {

	@Schema(description = "Grant Type", example = "Bearer", required = true)
	private String grantType;

	@Schema(description = "Access token", example = "eyJ0eXasIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE2ODI0OTg4OTUsImV4cCI6MTY4MjQ5OTc5NSwibWVtYmVySWQiOjEsInJvbGUiOiJVU0VSIn0.YYl9XmJ2JnJm1S-4eCf11faRTcVJj9cGdI3TlerbEu1Wf_XCi8FcRT_62jNrrMYfyj2chYMlL7Td7OSSh1vxFA", required = true)
	private String accessToken;

	@Schema(description = "Access Token 만료 시간", example = "2023-04-26 18:03:15", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date accessTokenExpireTime;
}
