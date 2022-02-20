package com.coltla.db;

import java.sql.SQLException;

/**
 * Connect to MySQL using a Maven project
 *
 */
public class App {
	public static void main(String[] args) {
		
		var db = Database.instance();
		
		try {
			db.connect();
		} catch (SQLException e) {
			System.out.println("Cannot connect to DB");
			e.printStackTrace();
		}
		
		System.out.println("Connected to DB");
		
		try {
			db.close();
		} catch (Exception e) {
			System.out.println("Cannot close DB connection");
			e.printStackTrace();
		}
	}
}
