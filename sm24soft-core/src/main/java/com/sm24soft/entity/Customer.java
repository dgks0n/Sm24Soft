package com.sm24soft.entity;

public class Customer extends Person {

	private String taxRegistrationNumber;
	
	/*
	 * Số điểm tích luỹ, điểm nhiều sẽ được ưu đãi cho những lần mua sau. G/S
	 * chiết khấu cho những khách hàng có điểm > 1000
	 */
	private int loyaltyPoint;

	public Customer() {
		super();
	}

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public int getLoyaltyPoint() {
		return loyaltyPoint;
	}

	public void setLoyaltyPoint(int loyaltyPoint) {
		this.loyaltyPoint = loyaltyPoint;
	}
	
}
