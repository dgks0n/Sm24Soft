package com.sm24soft.entity;

public class ItemCategory extends BaseEntity {

	private String name;
	private String description;
	private String parentItemCategoryCode;

	public ItemCategory() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getParentItemCategoryCode() {
		return parentItemCategoryCode;
	}

	public void setParentItemCategoryCode(String parentItemCategoryCode) {
		this.parentItemCategoryCode = parentItemCategoryCode;
	}

}
