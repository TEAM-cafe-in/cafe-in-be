package com.cafein.backend.support.utils;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class DataBaseSupporter {

	@Autowired
	private DatabaseCleanUp databaseCleaUp;

	@AfterEach
	void cleanUp() {
		databaseCleaUp.cleanDatabase();
	}
}
