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
	private String fax1;
	private String fax2;
	private String fax3;
	private String taxRegistrationNumber;
	private String businessLicenseNumber;
	private Date dateOfIssue;
	private String representativePersonCode;
	private List<BusinessRegisteredField> businessRegisteredFields;
	private String operatingStatus; // 0 = Normal, 1 = Stopped;
	private String websiteUrl;
	private String logo;
	private String bankNumber1;
	private String bankName1;
	private String bankNumber2;
	private String bankName2;
	private String bankNumber3;
	private String bankName3;

	public Store() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getFax2() {
		return fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	public String getFax3() {
		return fax3;
	}

	public void setFax3(String fax3) {
		this.fax3 = fax3;
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

	public String getRepresentativePersonCode() {
		return representativePersonCode;
	}

	public void setRepresentativePersonCode(String representativePersonCode) {
		this.representativePersonCode = representativePersonCode;
	}

	public List<BusinessRegisteredField> getBusinessRegisteredFields() {
		return businessRegisteredFields;
	}

	public void setBusinessRegisteredFields(List<BusinessRegisteredField> businessRegisteredFields) {
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBankNumber1() {
		return bankNumber1;
	}

	public void setBankNumber1(String bankNumber1) {
		this.bankNumber1 = bankNumber1;
	}

	public String getBankName1() {
		return bankName1;
	}

	public void setBankName1(String bankName1) {
		this.bankName1 = bankName1;
	}

	public String getBankNumber2() {
		return bankNumber2;
	}

	public void setBankNumber2(String bankNumber2) {
		this.bankNumber2 = bankNumber2;
	}

	public String getBankName2() {
		return bankName2;
	}

	public void setBankName2(String bankName2) {
		this.bankName2 = bankName2;
	}

	public String getBankNumber3() {
		return bankNumber3;
	}

	public void setBankNumber3(String bankNumber3) {
		this.bankNumber3 = bankNumber3;
	}

	public String getBankName3() {
		return bankName3;
	}

	public void setBankName3(String bankName3) {
		this.bankName3 = bankName3;
	}

}
