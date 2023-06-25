package com.cafein.backend.support.fixture;

import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;

import com.cafein.backend.api.cafe.dto.CafeInfoDTO;
import com.cafein.backend.api.cafe.dto.CafeInfoProjection;
import com.cafein.backend.api.member.dto.CafeInfoViewedByMemberProjection;

public class CafeFixture {

	public static final CafeInfoProjection CAFE_INFO_PROJECTION = mock(CafeInfoProjection.class);
	public static final CafeInfoViewedByMemberProjection CAFE_INFO_VIEWED_BY_MEMBER = mock(CafeInfoViewedByMemberProjection.class);
	public static final List<Long> VIEWED_CAFE_IDS = new ArrayList<>(List.of(1L, 2L, 3L));
	public static final List<String> CAFE_COMMENTS = new ArrayList<>(List.of("comment1", "comment2"));
	public static final CafeInfoDTO CAFE_INFO_RESPONSE_DTO = CafeInfoDTO.builder().build();
}
