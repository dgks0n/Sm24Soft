package com.sm24soft.entity;

import com.sm24soft.common.crypto.EncryptedPassword;

public class Account extends BaseEntity {

	private String username;
	private EncryptedPassword encryptedPassword;
	private String confirmationCode;
	private int role;
	
	/*
	 * 0 = NON_ACTIVE, 1 = ACTIVE
	 */
	private String status;
	
	/*
	 * 0 = Customer, 1 = Staff
	 */
	private String staffOrCustomerFlg;
	private Person staffOrCustomer;
	private Store store;
	
	/*
	 * 0 = Normal Account, 1 = Facebook Account
	 */
	private String signedUpWithFacebookFlg;
	
	/*
	 * 0 = Normal Account, 1 = Google Account
	 */
	private String signedUpWithGoogleFlg;
	
	/*
	 * Blog's URL
	 */
	private String sharingBlogUrl1;
	
	/*
	 * Blog's URL
	 */
	private String sharingBlogUrl2;
	
	/*
	 * Blog's URL
	 */
	private String sharingBlogUrl3;
	
	public Account() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EncryptedPassword getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(EncryptedPassword encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStaffOrCustomerFlg() {
		return staffOrCustomerFlg;
	}

	public void setStaffOrCustomerFlg(String staffOrCustomerFlg) {
		this.staffOrCustomerFlg = staffOrCustomerFlg;
	}

	public Person getStaffOrCustomer() {
		return staffOrCustomer;
	}

	public void setStaffOrCustomer(Person staffOrCustomer) {
		this.staffOrCustomer = staffOrCustomer;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getSignedUpWithFacebookFlg() {
		return signedUpWithFacebookFlg;
	}

	public void setSignedUpWithFacebookFlg(String signedUpWithFacebookFlg) {
		this.signedUpWithFacebookFlg = signedUpWithFacebookFlg;
	}

	public String getSignedUpWithGoogleFlg() {
		return signedUpWithGoogleFlg;
	}

	public void setSignedUpWithGoogleFlg(String signedUpWithGoogleFlg) {
		this.signedUpWithGoogleFlg = signedUpWithGoogleFlg;
	}

	public String getSharingBlogUrl1() {
		return sharingBlogUrl1;
	}

	public void setSharingBlogUrl1(String sharingBlogUrl1) {
		this.sharingBlogUrl1 = sharingBlogUrl1;
	}

	public String getSharingBlogUrl2() {
		return sharingBlogUrl2;
	}

	public void setSharingBlogUrl2(String sharingBlogUrl2) {
		this.sharingBlogUrl2 = sharingBlogUrl2;
	}

	public String getSharingBlogUrl3() {
		return sharingBlogUrl3;
	}

	public void setSharingBlogUrl3(String sharingBlogUrl3) {
		this.sharingBlogUrl3 = sharingBlogUrl3;
	}

}
