package com.sm24soft.entity;

public class Customer extends Person {

	private String taxRegistrationNumber;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}
}
