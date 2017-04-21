package com.sm24soft.entity;

import java.util.Date;

public class Staff extends Person {

	private Date signedContractDate;
	
	/*
	 * 6 months, 1 year, 2 years, and so on
	 */
	private String kindOfContract;
	
	/*
	 * full-time or part-time
	 */
	private String workingTime;

	public Staff() {
		super();
	}

	public Date getSignedContractDate() {
		return signedContractDate;
	}

	public void setSignedContractDate(Date signedContractDate) {
		this.signedContractDate = signedContractDate;
	}

	public String getKindOfContract() {
		return kindOfContract;
	}

	public void setKindOfContract(String kindOfContract) {
		this.kindOfContract = kindOfContract;
	}

	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

}
