package com.cafein.backend.api.comment.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cafein.backend.domain.commentkeyword.entity.CommentKeyword;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CommentInfoDTO {

	@Schema(name = "memberName", description = "회원 이름", example = "황의찬", required = true)
	private String memberName;

	@Schema(name = "createdTime", description = "댓글 등록 시간", example = "2023-06-27 17:42:08.671987", required = true)
	private LocalDateTime createdTime;

	@Schema(name = "content", description = "댓글 내용", example = "여기 카페 너무 트렌디해요!", required = true)
	private String content;

	@Schema(name = "keywords", description = "댓글 키워드", type = "array", example = "[청결도, 콘센트]", required = true)
	private List<CommentKeyword> keywords;
}
