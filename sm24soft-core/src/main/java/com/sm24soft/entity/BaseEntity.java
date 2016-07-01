package com.sm24soft.entity;

import java.util.Date;

public class BaseEntity extends Entity {

	private String DeleteFlg; // 0 = Normal, 1 = Deleted
	
	private String createdUserCode;
	private Date createdDateTime;
	private String updatedUserCode;
	private Date updatedDateTime;

	public BaseEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDeleteFlg() {
		return DeleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		DeleteFlg = deleteFlg;
	}

	public String getCreatedUserCode() {
		return createdUserCode;
	}

	public void setCreatedUserCode(String createdUserCode) {
		this.createdUserCode = createdUserCode;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getUpdatedUserCode() {
		return updatedUserCode;
	}

	public void setUpdatedUserCode(String updatedUserCode) {
		this.updatedUserCode = updatedUserCode;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

}
