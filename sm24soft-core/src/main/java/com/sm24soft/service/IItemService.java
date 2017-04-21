package com.sm24soft.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sm24soft.entity.Image;
import com.sm24soft.entity.Image.ImageType;
import com.sm24soft.entity.Item;

public interface IItemService extends IService {

	public static final String SERVICE_ID = "itemService";
	
	String createNewItem(Item item) throws IOException;
	
	String updateItem(Item item) throws IOException;
	
	String copyItemFromOtherItem(String oldItemId, Item newItem) throws IOException;
	
	Image uploadPreviewOrThumbnailImageForEachItem(final ImageType imageType, final File file);
	
	void deleteById(final String id);
	
	Item findById(final String id);
	
	List<Item> findAllBySupplierName(final String supplierName);
	
	List<Item> findAll();	
}
