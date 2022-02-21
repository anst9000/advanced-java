package com.coltla.db;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProfileTest {

	@Test
	public void loadDbConfigShouldWork() {
		
		var props = Profile.getProperties("db");		
		assertNotNull("Cannot load db properties", props);
		
		var dbName = props.getProperty("database");
		assertEquals("dbName incorrect", "testPeople", dbName);
	}
}
