package com.cafein.backend.support.fixture;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.BDDMockito;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.cafe.dto.CafeInfoProjection;
import com.cafein.backend.api.member.dto.CafeInfoViewedByMemberProjection;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.common.Address;

public class CafeFixture {

	public static final Cafe CAFE = createCafe();
	public static final CafeInfoDTO CAFE_INFO_DTO = cafeInfoDTO();
	public static final CafeInfoViewedByMemberProjection CAFE_INFO_VIEWED_BY_MEMBER = cafeInfoViewedByMember();
	public static final List<Long> VIEWED_CAFE_IDS = new ArrayList<>(List.of(1L, 2L, 3L));

	private static Cafe createCafe() {
		return Cafe.testBuilder()
			.cafeId(1L)
			.name("브레디포스트 성수")
			.info("[BREADYPOST FLAGSHIP STORE SEONGSU]")
			.local(Local.SEONGSU)
			.address(
				Address.builder()
					.sido("서울")
					.sigungu("서울시 성동구")
					.roadName("상원1길")
					.houseNumber("5 1층")
					.build()
			)
			.build();
	}

	private static CafeInfoDTO cafeInfoDTO() {
		return CafeInfoDTO.builder()
			.cafeInfoProjection(mock(CafeInfoProjection.class))
			.comments(new ArrayList<>())
			.build();
	}

	private static CafeInfoViewedByMemberProjection cafeInfoViewedByMember() {
		return mock(CafeInfoViewedByMemberProjection.class);
	}
}
