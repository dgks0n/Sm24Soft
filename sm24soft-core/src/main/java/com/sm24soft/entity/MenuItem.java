package com.sm24soft.entity;

public class MenuItem extends BaseEntity {

	private String fullNameOfMenuItem;
	private int roleOfMenuItem;
	private int orderNumber;
	private String orderType;
	private String fullPathOfMenuItem;
	
	/*
	 * 0: NON_ACTIVE, 1: ACTIVE
	 */
	private String actualStatus;
	
	public MenuItem() {
		super();
	}

	public String getFullNameOfMenuItem() {
		return fullNameOfMenuItem;
	}

	public void setFullNameOfMenuItem(String fullNameOfMenuItem) {
		this.fullNameOfMenuItem = fullNameOfMenuItem;
	}

	public int getRoleOfMenuItem() {
		return roleOfMenuItem;
	}

	public void setRoleOfMenuItem(int roleOfMenuItem) {
		this.roleOfMenuItem = roleOfMenuItem;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getFullPathOfMenuItem() {
		return fullPathOfMenuItem;
	}

	public void setFullPathOfMenuItem(String fullPathOfMenuItem) {
		this.fullPathOfMenuItem = fullPathOfMenuItem;
	}

	public String getActualStatus() {
		return actualStatus;
	}

	public void setActualStatus(String actualStatus) {
		this.actualStatus = actualStatus;
	}

}
