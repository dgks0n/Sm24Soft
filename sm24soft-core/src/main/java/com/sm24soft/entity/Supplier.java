package com.sm24soft.entity;

import java.util.List;

public class Supplier extends BaseEntity {

	private String companyName;
	private String companyTradingName;
	
	/*
	 * Người đại diện pháp lý của Trang Trại 
	 */
	private RepresentativeOrContactPerson representativePerson;
	
	/*
	 * Người liên lạc
	 */
	private RepresentativeOrContactPerson contactPerson;
	
	private String address1;
	private String address2;
	private String email;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String telephoneNumber3;
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	
	/*
	 * Điểm tích luỹ của Trang trại.
	 * 
	 * TH có nhiều sản phẩm của Trang Trại được bán, điều này đồng nghĩa
	 * với việc Trang Trại sẽ được cộng điểm.
	 */
	private int loyaltyAccumulatedPoint;
	private List<ItemCategory> listOfItemCategories;
	private List<Item> listOfItems;

	public Supplier() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTradingName() {
		return companyTradingName;
	}

	public void setCompanyTradingName(String companyTradingName) {
		this.companyTradingName = companyTradingName;
	}

	public RepresentativeOrContactPerson getRepresentativePerson() {
		return representativePerson;
	}

	public void setRepresentativePerson(RepresentativeOrContactPerson representativePerson) {
		this.representativePerson = representativePerson;
	}

	public RepresentativeOrContactPerson getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(RepresentativeOrContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber1() {
		return telephoneNumber1;
	}

	public void setTelephoneNumber1(String telephoneNumber1) {
		this.telephoneNumber1 = telephoneNumber1;
	}

	public String getTelephoneNumber2() {
		return telephoneNumber2;
	}

	public void setTelephoneNumber2(String telephoneNumber2) {
		this.telephoneNumber2 = telephoneNumber2;
	}

	public String getTelephoneNumber3() {
		return telephoneNumber3;
	}

	public void setTelephoneNumber3(String telephoneNumber3) {
		this.telephoneNumber3 = telephoneNumber3;
	}

	public String getFaxNumber1() {
		return faxNumber1;
	}

	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}

	public String getFaxNumber2() {
		return faxNumber2;
	}

	public void setFaxNumber2(String faxNumber2) {
		this.faxNumber2 = faxNumber2;
	}

	public String getFaxNumber3() {
		return faxNumber3;
	}

	public void setFaxNumber3(String faxNumber3) {
		this.faxNumber3 = faxNumber3;
	}
	
	public int getLoyaltyAccumulatedPoint() {
		return loyaltyAccumulatedPoint;
	}

	public void setLoyaltyAccumulatedPoint(int loyaltyAccumulatedPoint) {
		this.loyaltyAccumulatedPoint = loyaltyAccumulatedPoint;
	}

	public List<ItemCategory> getListOfItemCategories() {
		return listOfItemCategories;
	}

	public void setListOfItemCategories(List<ItemCategory> listOfItemCategories) {
		this.listOfItemCategories = listOfItemCategories;
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

}
