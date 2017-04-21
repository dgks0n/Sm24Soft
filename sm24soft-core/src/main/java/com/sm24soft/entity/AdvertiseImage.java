package com.sm24soft.entity;

import org.apache.commons.lang.StringUtils;

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
	
	public String getShortAdvertiseTitle() {
		if (StringUtils.isEmpty(advertiseTitle)) {
			return advertiseTitle;
		}
		if (advertiseTitle.length() <= 50) {
			return advertiseTitle;
		}
		return advertiseTitle.substring(0, 50) + "...";
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
	
	public String getShortAdvertisePath() {
		if (StringUtils.isEmpty(advertisePath)) {
			return advertisePath;
		}
		if (advertisePath.length() <= 50) {
			return advertisePath;
		}
		return advertisePath.substring(0, 50) + "...";
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
