package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.ItemCategory;

public interface ItemCategoryRepository extends CrudRepository<ItemCategory, String> {

	List<ItemCategory> findAllBySupplierId(String id);
}
