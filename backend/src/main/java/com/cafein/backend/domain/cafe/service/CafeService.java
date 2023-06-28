package com.cafein.backend.domain.cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.comment.dto.CommentInfoDTO;
import com.cafein.backend.api.home.dto.HomeResponseDTO;
import com.cafein.backend.api.member.dto.CafeInfoViewedByMemberProjection;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.global.error.ErrorCode;
import com.cafein.backend.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CafeService {

	private final CafeRepository cafeRepository;

	@Transactional(readOnly = true)
	public HomeResponseDTO getHomeData(Long memberId) {
		return HomeResponseDTO.builder()
			.cafeCount(cafeRepository.count())
			.cafes(cafeRepository.getHomeData(memberId))
			.build();
	}

	@Transactional(readOnly = true)
	public CafeInfoDTO findCafeInfoById(Long memberId, Long cafeId) {
		return CafeInfoDTO.builder()
			.cafeInfoProjection(cafeRepository.findCafeInfoById(memberId, cafeId))
			.comments(getComments(cafeId))
			.build();
	}

	private List<CommentInfoDTO> getComments(final Long cafeId) {
		final List<Comment> comments = cafeRepository.findAllCommentByCafeId(cafeId);
		return comments.stream()
			.map(comment -> CommentInfoDTO.builder()
				.memberName(comment.getMember().getName())
				.createdTime(comment.getCreatedTime())
				.content(comment.getContent())
				.keywords(comment.getKeywords())
				.build())
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<CafeInfoViewedByMemberProjection> findCafeInfoViewedByMember(final List<Long> viewedCafeIds) {
		return viewedCafeIds.stream()
			.map(cafeRepository::findCafeInfoViewedByMember)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Cafe findCafeByCafeId(final Long cafeId) {
		return cafeRepository.findById(cafeId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.CAFE_NOT_EXIST));
	}
}
