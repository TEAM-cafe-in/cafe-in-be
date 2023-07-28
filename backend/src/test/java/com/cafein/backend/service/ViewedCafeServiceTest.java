package com.cafein.backend.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cafein.backend.domain.viewedcafe.repository.ViewedCafeRepository;
import com.cafein.backend.domain.viewedcafe.service.ViewedCafeService;
import com.cafein.backend.global.error.exception.BusinessException;
import com.cafein.backend.support.utils.ServiceTest;

@ServiceTest
class ViewedCafeServiceTest {

	@InjectMocks
	private ViewedCafeService viewedCafeService;

	@Mock
	private ViewedCafeRepository viewedCafeRepository;

	@Test
	void 오늘_처음_커피콩을_사용해_보낸_조회_요청을_검증한다() {
		given(viewedCafeRepository.validateCongestionRequest(anyLong(), anyLong())).willReturn(0);

		viewedCafeService.validateCongestionRequest(1L, 1L);

		then(viewedCafeRepository).should(times(1)).validateCongestionRequest(anyLong(), anyLong());
	}

	@Test
	void 오늘_이미_커피콩을_사용해_조회한_카페에_보내진_요청을_검증한다() {
		given(viewedCafeRepository.validateCongestionRequest(anyLong(), anyLong())).willReturn(1);

		assertThatThrownBy(() -> viewedCafeService.validateCongestionRequest(1L, 1L))
			.isInstanceOf(BusinessException.class)
			.hasMessage("오늘 이미 혼잡도를 요청한 카페입니다.");
	}
}
