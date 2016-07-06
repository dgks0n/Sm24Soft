package com.sm24soft.entity;

import java.util.List;

public class ItemCategory extends BaseEntity {

	private String name;
	private String description;
	private ItemCategory parentItemCategory;
	
	/*
	 * Belong to what supplier
	 */
	private Supplier supplier;
	
	// Extends
	private List<ItemCategory> listOfItemCategories;
	private List<Item> listOfItems;

	public ItemCategory() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ItemCategory getParentItemCategory() {
		return parentItemCategory;
	}

	public void setParentItemCategory(ItemCategory parentItemCategory) {
		this.parentItemCategory = parentItemCategory;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<ItemCategory> getListOfItemCategories() {
		return listOfItemCategories;
	}

	public void setListOfItemCategories(List<ItemCategory> listOfItemCategories) {
		this.listOfItemCategories = listOfItemCategories;
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

}
