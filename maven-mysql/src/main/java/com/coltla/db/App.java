package com.coltla.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Connect to MySQL using a Maven project
 *
 */
public class App {
	private static IUserDao userDao = new UserDaoImpl();
	
	public static void main(String[] args) {
		
		Properties props = Profile.getProperties("db");
		
		var db = Database.instance();
		
		try {
			db.connect(props);
		} catch (SQLException e) {
			System.out.println("Cannot connect to DB");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Connected to DB");
//		IUserDao userDao = new UserDaoImpl();

//		savePeople();
		retrieveSinglePerson(1);
		retrieveAllPeople();
		removePerson(6);
		updatePerson(4, "Andrea");
		retrieveAllPeople();
		
		try {
			db.close();
		} catch (Exception e) {
			System.out.println("Cannot close DB connection");
			e.printStackTrace();
		}
	}
	
	private static void updatePerson(int id, String name) {
		var userOpt = userDao.findById(id);
		User user = null;
		
		if (userOpt.isPresent()) {
			user = userOpt.get();
			user.setId(id);
			user.setName(name);

			System.out.println("--> " + user);
			userDao.update(user);
		}
		
		userDao.update(user);
	}

	private static void removePerson(int id) {
		userDao.delete(id);
	}

	public static void savePeople() {
		List<String> persons = new ArrayList<>();
		persons.add("Sue");
		persons.add("Bob");
		persons.add("Charley");


		for (String person : persons) {
			userDao.save(new User(person));
		}
	}
	
	public static void retrieveSinglePerson(int id) {
		var userOpt = userDao.findById(id);

		if (userOpt.isPresent()) {
			System.out.println("--> " + userOpt.get());
		} else {
			System.out.println("No user with id " + id + " exists.");
		}
	}
	
	public static void retrieveAllPeople() {
		var users = userDao.getAll();
		users.forEach(System.out::println);
	}
}
