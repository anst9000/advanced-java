package com.coltla.db;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {

	void save(T t);
	
	Optional<T> findById(int id);

	void update(User user);
	
	void delete(int id);
	
	List<T> getAll();
}
