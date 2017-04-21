package com.sm24soft.repository;

import java.util.List;

public interface CrudRepository<T, PK> extends Repository<T, PK> {
	
	<S extends T> void save(S entity);
	
	<S extends T> void update(S entity);
	
	T findById(PK id);
	
	List<T> findAll();
	
	void deleteById(PK id);
}
