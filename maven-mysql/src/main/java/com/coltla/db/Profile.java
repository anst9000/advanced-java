package com.coltla.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Profile {

	public static Properties getProperties(String name) {
		Properties props = new Properties();
		String env = System.getProperty("env");
		
		if (env == null) {
			env = "dev";
		}

		String propertiesFile = String.format("/config/%s.%s.properties", name, env);
		System.out.println(propertiesFile);

		try {
			InputStream inStream = App.class.getResourceAsStream(propertiesFile); 
			props.load(inStream);
		} catch (NullPointerException | IOException ex) {
			throw new RuntimeException("Cannot load properties file, " + propertiesFile);
		}
		
		return props;
	}
}
