package com.coltla.db;

import java.util.Optional;

public interface IDao<T> {

	void save(T t);
	
	Optional<T> findById(int id);

	void update(T t);
	
	void delete(int id);
}
