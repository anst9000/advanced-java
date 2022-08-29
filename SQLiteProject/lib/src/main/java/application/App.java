package application;

import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Person[] people = { 
				new Person(1, "Bob"), 
				new Person(2, "Andy"),
				new Person(3, "Sarah")
		};
		
		Class.forName("org.sqlite.JDBC");

		String dbUrl = "jdbc:sqlite:people.db";
		var conn = DriverManager.getConnection(dbUrl);

		var stmt = conn.createStatement();
		conn.setAutoCommit(false);
		
		var query = "CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name TEXT NOT NULL)";
		stmt.execute(query);
		
		query = "INSERT INTO user (id, name) VALUES (?, ?)";
		var insertStmt = conn.prepareStatement(query);
		
		for (Person person : people) {
			System.out.println(person);
			insertStmt.setInt(1, person.getId());
			insertStmt.setString(2,  person.getName());
			
//			insertStmt.executeUpdate();
		}
		
		conn.commit();
		
		insertStmt.close();

		query = "SELECT id, name FROM user";
		var rs = stmt.executeQuery(query);

		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			
			System.out.println(id + ": " + name);
		}
		
		query = "DROP TABLE user";
//		stmt.execute(query);
		
		stmt.close();		

		conn.close();
	}
}
