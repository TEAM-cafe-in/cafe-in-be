package com.cafein.backend.api.cafe.dto;

import java.util.List;

import com.cafein.backend.api.comment.dto.CommentInfoDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@JsonPropertyOrder({
	"cafeInfo",
	"comment"
})
@Getter @Builder
public class CafeDTO {

	@JsonProperty("cafeInfo")
	private CafeInfoProjection cafeInfoProjection;

	@Schema(name = "comment", description = "카페 리뷰", type = "array", example = "[commentId, memberName, createdTime, content, keywords = []]", required = true)
	private List<CommentInfoDTO> comments;
}
