package com.coltla.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements IUserDao {

	@Override
	public void save(User user) {
		var conn = Database.instance().getConnection();
		String query = "INSERT INTO users (name) VALUES (?)";

		try {
			var stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getName());
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public Optional<User> findById(int id) {
		var conn = Database.instance().getConnection();
		String query = "SELECT name FROM users WHERE id=?";

		ResultSet rs = null;

		try {
			var stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			
			rs = stmt.executeQuery();

			if (rs.next()) {
				var name = rs.getString("name");
				User user = new User(name);

				return Optional.of(user);
			}

			stmt.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}
	
		return Optional.empty();
	}

	@Override
	public void update(User user) {
		var conn = Database.instance().getConnection();
		String query = "UPDATE users SET name=? WHERE id=?";

		try {
			var stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getName());
			stmt.setInt(2, user.getId());
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public void delete(int id) {
		var conn = Database.instance().getConnection();
		String query = "DELETE FROM users WHERE id=?";

		try {
			var stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();

		var conn = Database.instance().getConnection();
		String query = "SELECT id, name FROM users";
		ResultSet rs = null;
		User user = null;

		try {
			var stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				var id = rs.getInt("id");
				var name = rs.getString("name");
				
				users.add(new User(id, name));
			}
			
			stmt.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}
		
		return users;
	}

}
