package com.sm24soft.entity;

public class BusinessRegisteredField extends BaseEntity {

	private String shortName;
	private String fullName;
	private String description;

	public BusinessRegisteredField() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
