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

//		int[] ids = { 1, 2, 3 };
//		String[] names = { "Sue", "Bob", "Charley" };
		
		Class.forName("org.sqlite.JDBC");
		
		String dbUrl = "jdbc:sqlite:res/people.db";
		
		var conn = DriverManager.getConnection(dbUrl);
		var stmt = conn.createStatement();
		conn.setAutoCommit(false);;
		
		
		// Create table 'user'
		var sql = "create table if not exists user (id integer primary key, name text not null)";
		stmt.execute(sql);


		// Insert entries to DB people 
		sql = "insert into user (id, name) values(?, ?)";
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
		sql = "select id, name from user";
		var rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			
			System.out.println("id is " + id + " and name is " + name);
		}
		
		sql = "drop table user";
//		stmt.execute(sql);
		
		stmt.close();		
		conn.close();
	}

}
