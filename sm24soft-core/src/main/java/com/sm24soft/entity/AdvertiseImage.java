package com.sm24soft.entity;

public class AdvertiseImage extends BaseEntity {

	private String advertiseTitle;
	private String advertiseUrl;
	private String advertisePath;
	
	/*
	 * 0: ACTIVE
	 * 1: NON_ACTIVE
	 * 
	 * Default is 0
	 */
	private String status;

	public String getAdvertiseTitle() {
		return advertiseTitle;
	}

	public void setAdvertiseTitle(String advertiseTitle) {
		this.advertiseTitle = advertiseTitle;
	}

	public String getAdvertiseUrl() {
		return advertiseUrl;
	}

	public void setAdvertiseUrl(String advertiseUrl) {
		this.advertiseUrl = advertiseUrl;
	}

	public String getAdvertisePath() {
		return advertisePath;
	}

	public void setAdvertisePath(String advertisePath) {
		this.advertisePath = advertisePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
