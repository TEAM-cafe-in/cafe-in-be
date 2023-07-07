package com.cafein.backend.api.cafe.dto;

import java.util.List;

import com.cafein.backend.api.comment.dto.CommentInfoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CafeInfoDTO {

	private CafeInfoProjection cafeInfoProjection;

	@Schema(name = "comment", description = "카페 리뷰", type = "array", example = "[commentId, memberName, createdTime, content, keywords = []]", required = true)
	private List<CommentInfoDTO> comments;
}
