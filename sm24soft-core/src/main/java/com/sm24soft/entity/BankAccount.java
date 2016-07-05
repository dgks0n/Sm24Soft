package com.sm24soft.entity;

public class BankAccount extends BaseEntity {

	private String bankNumber;
	private String bankName;
	
	public BankAccount() {
		super();
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
