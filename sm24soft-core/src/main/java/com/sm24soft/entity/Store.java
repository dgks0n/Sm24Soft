package com.sm24soft.entity;

import java.util.Date;
import java.util.List;

public class Store extends BaseEntity {

	private String name;
	private String tradingName;
	private String address1;
	private String address2;
	private String email;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String telephoneNumber3;
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	private String taxRegistrationNumber;
	private String businessLicenseNumber;
	private Date dateOfIssue;
	private RepresentativeOrContactPerson representativePerson;
	private String businessRegisteredFields; // Intending storage under JSON String
	private String operatingStatus; // 0 = Normal, 1 = Stopped;
	private String websiteUrl;
	private String logoUrl;
	
	// Extends
	private List<BankAccount> listOfBanks;

	public Store() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
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

	public String getTaxRegistrationNumber() {
		return taxRegistrationNumber;
	}

	public void setTaxRegistrationNumber(String taxRegistrationNumber) {
		this.taxRegistrationNumber = taxRegistrationNumber;
	}

	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public RepresentativeOrContactPerson getRepresentativePerson() {
		return representativePerson;
	}

	public void setRepresentativePerson(RepresentativeOrContactPerson representativePerson) {
		this.representativePerson = representativePerson;
	}

	public String getBusinessRegisteredFields() {
		return businessRegisteredFields;
	}

	public void setBusinessRegisteredFields(String businessRegisteredFields) {
		this.businessRegisteredFields = businessRegisteredFields;
	}

	public String getOperatingStatus() {
		return operatingStatus;
	}

	public void setOperatingStatus(String operatingStatus) {
		this.operatingStatus = operatingStatus;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public List<BankAccount> getListOfBanks() {
		return listOfBanks;
	}

	public void setListOfBanks(List<BankAccount> listOfBanks) {
		this.listOfBanks = listOfBanks;
	}

}
