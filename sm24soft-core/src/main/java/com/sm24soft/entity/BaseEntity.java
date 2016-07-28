package com.sm24soft.entity;

import java.util.Date;

import com.sm24soft.common.util.DateUtil;

public class BaseEntity extends Entity {
	
	private final String DEFAULT_USER = "00000000";
	
	/**
	 * Actual status for one entity (RECORD in underlying database)
	 * 
	 * @author sondn
	 *
	 */
	public enum EntityStatus {
		NON_ACTIVE("1"),
		ACTIVE("0");
		
		private String actualStatus;
		
		EntityStatus(String actualStatus) {
			this.actualStatus = actualStatus;
		}
		
		public String value() {
			return this.actualStatus;
		}
	}
	
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
		// Default setting
		this.deleteFlg = EntityStatus.ACTIVE.value();
		this.createdAt = DateUtil.now();
		this.createdUserId = DEFAULT_USER;
		this.updatedAt = DateUtil.now();
		this.updatedUserId = DEFAULT_USER;
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

	@Deprecated
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

	@Deprecated
	public void setCreatedUserIdAsDefault() {
		this.createdUserId = DEFAULT_USER;
	}
	
	@Deprecated
	public void setUpdatedUserIdAsDefault() {
		this.updatedUserId = DEFAULT_USER;
	}
}
