package com.sm24soft.entity;

import java.util.Date;

import com.sm24soft.common.util.DateUtil;

public class BaseEntity extends Entity {
	
	private final String DEFAULT_SYSTEM_USER = "00000000";
	
	/*
	 * 0 = Normal, 1 = Deleted
	 */
	private String deleteFlg = "0";
	
	private String createdUserId;
	private String updatedUserId;
	
	/*
	 * Default is current system date
	 */
	private Date createdAt = DateUtil.now();
	
	/*
	 * Default is current system date
	 */
	private Date updatedAt = DateUtil.now();

	public BaseEntity() {
		super();
	}

	public String getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Default is system user
	 */
	public void setCreatedUserIdAsDefault() {
		this.createdUserId = DEFAULT_SYSTEM_USER;
	}
	
	/**
	 * Default is system user
	 */
	public void setUpdatedUserIdAsDefault() {
		this.updatedUserId = DEFAULT_SYSTEM_USER;
	}
}
