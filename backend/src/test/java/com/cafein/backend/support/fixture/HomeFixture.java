package com.cafein.backend.support.fixture;

import java.util.List;

import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import com.cafein.backend.api.home.dto.HomeProjection;

public class HomeFixture {

	public static List<HomeProjection> HOME_PROJECTION = createHomeProjection();

	private static List<HomeProjection> createHomeProjection() {
		HomeProjection projection = new SpelAwareProxyProjectionFactory().createProjection(HomeProjection.class);

		projection.setName("5to7");

		return List.of(projection);
	}
}
