package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.MenuItem;

public interface MenuItemRepository extends Repository<MenuItem, String> {

	/**
	 * Returns all instances of the type.
	 * 
	 * @return
	 */
	List<MenuItem> findAllMenuItem();
}
