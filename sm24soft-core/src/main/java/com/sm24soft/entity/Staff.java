package com.sm24soft.entity;

import java.util.Date;

public class Staff extends Person {

	private Date signedContractDate;
	private String kindOfContract; // 6 months, 1 year, 2 years, and so on
	private String workingTime; // full-time or part-time

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
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
