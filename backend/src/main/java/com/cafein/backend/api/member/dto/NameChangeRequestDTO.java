package com.cafein.backend.api.member.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class NameChangeRequestDTO {

	@Schema(name = "name", description = "변경하고 싶은 닉네임", example = "커피몬스터", required = true)
	@NotNull
	private String name;
}
