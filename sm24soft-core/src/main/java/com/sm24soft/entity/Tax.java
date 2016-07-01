package com.sm24soft.entity;

public class Tax extends BaseEntity {

	private String description;
	private String taxCalculation;

	public Tax() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTaxCalculation() {
		return taxCalculation;
	}

	public void setTaxCalculation(String taxCalculation) {
		this.taxCalculation = taxCalculation;
	}

}
