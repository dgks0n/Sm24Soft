package com.sm24soft.repository;

import java.io.Serializable;

public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {
	
	<S extends T> ID save(S entity);
	
	<S extends T> ID update(S entity);
	
	T findById(ID id);
	
	Iterable<T> findAll();
	
	void deleteById(ID id);
}
