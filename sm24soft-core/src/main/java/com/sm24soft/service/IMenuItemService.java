package com.sm24soft.service;

import java.util.List;

import com.sm24soft.entity.MenuItem;

public interface IMenuItemService extends IService {
	
	static final String SERVICE_ID = "menuItemService";

	String creatNewMenuItem(MenuItem menuItem);
	
	String updateMenuItem(MenuItem menuItem);
	
	MenuItem findById(String id);
	
	List<MenuItem> findAllMenuItems();
	
	void deleteById(String id);
}
