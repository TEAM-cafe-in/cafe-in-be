package com.cafein.backend.domain.cafe.constant;


public enum Local {

	HONGDAE, YEONNAM, SEONGSU;

	public static Local from(String local) {
		return Local.valueOf(local.toUpperCase());
	}
}
