package com.cafein.backend.api.review.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafein.backend.api.review.dto.ReviewDTO;
import com.cafein.backend.domain.review.service.ReviewService;
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
public class ReviewController {

	private final ReviewService reviewService;


	@Tag(name = "cafe")
	@Operation(summary = "카페 리뷰 등록 API", description = "카페에 리뷰를 등록하면 커피빈을 지급하고 업데이트된 커피빈 개수를 반환하는 API 입니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "R-001", description = "해당 카페에 대해 하루에 한번만 리뷰를 작성할 수 있습니다.")
	})
	@PostMapping("/cafe/{cafeId}/review")
	public ResponseEntity<ReviewDTO.Response> createCafeReview(@Valid @RequestBody ReviewDTO.Request requestDTO,
		 													   @ApiIgnore @MemberInfo MemberInfoDTO memberInfoDTO,
							  					 			   @PathVariable Long cafeId) {
		// TODO 동시성 처리 필요
		log.debug("memberId = {}", memberInfoDTO.getMemberId());
		reviewService.validateReview(memberInfoDTO.getMemberId(), cafeId);
		ReviewDTO.Response response = reviewService.createReview(requestDTO, cafeId, memberInfoDTO.getMemberId());
		return ResponseEntity.ok(response);
	}
}
