package com.sm24soft.service;

import java.util.List;

import com.sm24soft.entity.ItemCategory;

public interface IItemCategoryService extends IService {

	public static final String SERVICE_ID = "itemCategoryService";
	
	String createNew(ItemCategory itemCategory);
	
	String updateById(ItemCategory itemCategory);
	
	ItemCategory findById(String id);
	
	List<ItemCategory> findAllBySupplierId(String supplierId);
	
	List<ItemCategory> findAllBySupplierName(String supplierName);
	
	List<ItemCategory> findAllByMenuItemId(String menuItemId);
	
	List<ItemCategory> findAll();
	
	void deleteById(String id);
}
