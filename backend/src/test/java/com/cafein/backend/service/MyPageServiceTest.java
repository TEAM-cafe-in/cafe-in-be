package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cafein.backend.api.member.dto.MyPageDTO;
import com.cafein.backend.api.member.service.MyPageService;
import com.cafein.backend.domain.Review.respository.ReviewRepository;
import com.cafein.backend.domain.cafe.service.CafeService;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class MyPageServiceTest {

	@Autowired
	private MyPageService myPageService;

	@MockBean
	private ReviewRepository reviewRepository;

	@MockBean
	private CafeService cafeService;

	@Test
	void 회원이_최근_본_매장과_작성한_게시물_정보를_반환한다() {
		given(cafeService.findCafeInfoViewedByMember(any())).willReturn(Collections.emptyList());
		given(reviewRepository.findReviewsByMemberId(anyLong())).willReturn(Collections.emptyList());
		given(reviewRepository.countReviewByMemberId(anyLong())).willReturn(5L);

		final MyPageDTO myPageDTO = myPageService.getMyPageDTO(1L);

		assertThat(myPageDTO.getReviewCount()).isEqualTo(5L);
	}
}
