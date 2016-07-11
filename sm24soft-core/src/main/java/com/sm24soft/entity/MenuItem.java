package com.sm24soft.entity;

import com.sm24soft.common.util.StringFormatUtil;

public class MenuItem extends BaseEntity {

	private String fullNameOfMenuItem;
	
	/*
	 * 0: ROLE_ADMIN, 1: ROLE_USER 
	 */
	private int roleOfMenuItem = 0;
	private int orderNumber;
	private String orderType = "ASC";
	private String fullPathOfMenuItem;
	
	/*
	 * 0: NON_ACTIVE, 1: ACTIVE
	 */
	private String actualStatus = "1"; // Default is ACTIVE
	
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
	
	public String getRoleOfMenuItemAsString() {
		if (0 == getRoleOfMenuItem()) {
			return "Administrator";
		}
		return "Users";
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
	
	public String getActualStatusAsString() {
		StringBuilder builder = new StringBuilder("<button type=\"button\" class=\"btn ");
		if ("0".equals(getActualStatus())) {
			builder.append("btn-warning btn-xs\">Active");
		} else {
			builder.append("btn-success btn-xs\">Non-Active");
		}
		builder.append("</button>");
		return builder.toString();
	}

	public void setActualStatus(String actualStatus) {
		this.actualStatus = actualStatus;
	}
	
	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(2, getId());
	}

}
