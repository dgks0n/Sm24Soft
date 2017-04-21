package com.sm24soft.entity;

import java.util.List;

import com.sm24soft.common.util.StringFormatUtil;

public class ItemCategory extends BaseEntity {

	private String name;
	private String description;
	
	/*
	 * Belong to what menu item
	 */
	private MenuItem menuItem;
	
	/*
	 * Belong to what supplier
	 */
	private Supplier supplier;
	
	/*
	 * List of items (products)
	 */
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

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}
	
	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(4, getId());
	}

}
