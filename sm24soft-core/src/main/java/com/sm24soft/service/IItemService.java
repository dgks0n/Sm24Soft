package com.sm24soft.service;

import java.util.List;

import com.sm24soft.entity.Item;

public interface IItemService extends IService {

	public static final String SERVICE_ID = "itemService";
	
	String createNewItem(Item item);
	
	String updateItem(Item item);
	
	String copyItemFromAnotherItem(String originalItemId, Item customizeItem);
	
	String uploadImageForItem(final String imageId, final String imageField, 
			final String imageUrl);
	
	void deleteById(final String id);
	
	Item findById(final String id);
	
	List<Item> findAllBySupplierName(final String supplierName);
	
	List<Item> findAll();
	
}
