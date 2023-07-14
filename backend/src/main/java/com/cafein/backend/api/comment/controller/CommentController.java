package com.cafein.backend.api.comment.controller;

import static com.cafein.backend.api.comment.dto.CommentDTO.*;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.domain.comment.service.CommentService;
import com.cafein.backend.global.resolver.MemberInfo;
import com.cafein.backend.global.resolver.MemberInfoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "cafe", description = "카페 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentService commentService;

	@Tag(name = "cafe")
	@Operation(summary = "카페 댓글 등록 API", description = "카페에 댓글을 추가하는 API 입니다. 키워드는 중복 선택이 가능합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "CO-001", description = "해당 키워드는 존재하지 않습니다.")
	})
	@PostMapping("/cafe/{cafeId}/comment")
	public ResponseEntity<String> addComment(@Valid @RequestBody CommentRequest commentRequestDTO,
										     @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO,
										     @PathVariable Long cafeId) {
		final Long commentId = commentService.addComment(commentRequestDTO, cafeId, memberInfoDTO.getMemberId());
		return ResponseEntity.created(URI.create("/api/cafe/" + cafeId + "/comment/" + commentId)).build();
	}

	@Tag(name = "cafe")
	@Operation(summary = "카페 댓글 수정 API", description = "카페의 댓글을 수정하는 API 입니다. 키워드는 중복 선택이 가능합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "CO-002", description = "카페에 해당하는 댓글이 존재하지 않습니다.")
	})
	@PatchMapping("/cafe/{cafeId}/comment/{commentId}")
	public ResponseEntity<String> updateComment(@Valid @RequestBody CommentRequest commentRequestDTO,
												@PathVariable Long cafeId,
												@PathVariable Long commentId) {
		commentService.updateComment(commentRequestDTO, cafeId, commentId);
		return ResponseEntity.ok("comment updated");
	}

	@Tag(name = "cafe")
	@Operation(summary = "카페 댓글 삭제 API", description = "카페의 댓글을 삭제하는 API 입니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "CO-002", description = "카페에 해당하는 댓글이 존재하지 않습니다.")
	})
	@DeleteMapping("/cafe/{cafeId}/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Long cafeId,
												@PathVariable Long commentId) {
		commentService.deleteComment(cafeId, commentId);
		return ResponseEntity.ok("comment deleted");
	}
}
