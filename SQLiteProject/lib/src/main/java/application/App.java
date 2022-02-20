package application;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class App {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Map<Integer, String> persons = new HashMap<>();
		persons.put(1, "Sue");
		persons.put(2, "Bob");
		persons.put(3, "Charley");

		// Connection string for SQLite
//		Class.forName("org.sqlite.JDBC");
//		String dbUrl = "jdbc:sqlite:res/people.db";

		// Connection string for MySQL
		Class.forName("com.mysql.cj.jdbc.Driver");
		String dbUrl = "jdbc:mysql://localhost:3306/people";

		
		var conn = DriverManager.getConnection(dbUrl, "coltla", "ksbv27");
		var stmt = conn.createStatement();
		conn.setAutoCommit(false);;
		

		// Insert entries to DB people 
		var sql = "insert into users (id, name) values(?, ?)";
		var insertStmt = conn.prepareStatement(sql);

		for (Map.Entry<Integer, String> entry : persons.entrySet()) {
	    	int id = entry.getKey();
	    	String name = entry.getValue();
	    	insertStmt.setInt(1, id);
	    	insertStmt.setString(2, name);
	    	
//	    	insertStmt.executeUpdate();
		}
	    
	    conn.commit();	    
		insertStmt.close();


		
		// Request info from DB
		sql = "select id, name from users";
		var rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			
			System.out.println("id is " + id + " and name is " + name);
		}

		
		stmt.close();		
		conn.close();
	}

}
