package com.cafein.backend.support.fixture;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.cafe.dto.CafeInfoProjection;
import com.cafein.backend.api.member.dto.CafeInfoViewedByMemberProjection;

public class CafeFixture {

	public static final CafeInfoProjection CAFE_INFO_PROJECTION = createCafeInfoProjection();
	public static final CafeInfoDTO CAFE_INFO_DTO = createCafeInfoDTO();
	public static final CafeInfoViewedByMemberProjection CAFE_INFO_VIEWED_BY_MEMBER = mock(CafeInfoViewedByMemberProjection.class);
	public static final List<Long> VIEWED_CAFE_IDS = new ArrayList<>(List.of(1L, 2L, 3L));

	private static CafeInfoDTO createCafeInfoDTO() {
		return CafeInfoDTO.builder()
			.cafeInfoProjection(CAFE_INFO_PROJECTION)
			.comments(Collections.emptyList())
			.build();
	}

	private static CafeInfoProjection createCafeInfoProjection() {
		ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
		CafeInfoProjection projection = factory.createProjection(CafeInfoProjection.class);
		projection.setName("5to7");
		projection.setCafeId("1");
		projection.setPhoneNumber("050713337616");
		projection.setAddress("서울시 성동구 서울숲2길44-13 1층");
		projection.setStatus("영업중");
		projection.setAverageCongestion("2");
		projection.setLocal("SEONGSU");
		projection.setLatitude("37.5460707");
		projection.setLongitude("127.043297");
		projection.setHasPlugCount("5");
		projection.setIsCleanCount("5");
		return projection;
	}
}
