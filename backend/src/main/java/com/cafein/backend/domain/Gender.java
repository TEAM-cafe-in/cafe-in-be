package com.cafein.backend.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

	MALE("male"), FEMALE("female");

	private final String name;

	Gender(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}
