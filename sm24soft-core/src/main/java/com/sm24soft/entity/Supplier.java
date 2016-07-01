/**
 * 
 */
package com.sm24soft.entity;

/**
 * @author sondn
 *
 */
public class Supplier extends BaseEntity {

	private String companyName;
	private String companyTradingName;
	private String contactPersonCode;
	private String city;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String telephoneNumber3;
	private String fax1;
	private String fax2;
	private String fax3;

	public Supplier() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getContactPersonCode() {
		return contactPersonCode;
	}

	public void setContactPersonCode(String contactPersonCode) {
		this.contactPersonCode = contactPersonCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

}
