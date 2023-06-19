package com.cafein.backend.global.util;

import com.cafein.backend.domain.common.Address;

public class AddressUtils {

	public static String valueOf(Address address) {
		StringBuilder builder = new StringBuilder();
		builder.append(address.getSigungu());
		builder.append(" ");
		builder.append(address.getRoadName());
		builder.append(address.getHouseNumber());
		return builder.toString();
	}
}
