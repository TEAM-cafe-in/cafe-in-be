package com.cafein.backend.support.fixture;

import java.util.List;

import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import com.cafein.backend.api.home.dto.HomeProjection;

public class HomeFixture {

	public static List<HomeProjection> HOME_PROJECTION = createHomeProjection();

	private static List<HomeProjection> createHomeProjection() {
		HomeProjection projection = new SpelAwareProxyProjectionFactory().createProjection(HomeProjection.class);

		projection.setName("5to7");
		projection.setCafeId("1");
		projection.setPhoneNumber("05012341234");
		projection.setAddress("서울시 성동구 서울숲2길44-13 1층");
		projection.setCommentReviewCount("10");
		projection.setStatus("영업중");
		projection.setAverageCongestion("2");
		projection.setLocal("SEONGSU");
		projection.setLatitude("37.5460707");
		projection.setLongitude("127.043297");
		return List.of(projection);
	}
}
