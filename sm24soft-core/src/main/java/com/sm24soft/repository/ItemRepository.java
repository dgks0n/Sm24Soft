package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.Item;

public interface ItemRepository extends CrudRepository<Item, String> {

	List<Item> findAllByItemCategoryId(String id);
	
	List<Item> findAllBySupplierName(String supplierName);
}
