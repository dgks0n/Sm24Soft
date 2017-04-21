package com.sm24soft.entity;

public class Tax extends BaseEntity {

	private String description;
	private String calculation;

	public Tax() {
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCalculation() {
		return calculation;
	}

	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}

}
