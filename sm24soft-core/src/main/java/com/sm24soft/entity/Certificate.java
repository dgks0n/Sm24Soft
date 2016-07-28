package com.sm24soft.entity;

import org.apache.commons.lang.StringUtils;

public class Certificate extends BaseEntity {

	private String name;
	
	/* 
	 * Should use Text Editor for this field at client side
	 */
	private String description;
	private String detailsLinkUrl;
	
	private Supplier supplier;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		if (StringUtils.isEmpty(description)) {
			return null;
		}
		if (description.length() < 70) {
			return description;
		}
		// Default is get 70 first characters
		return description.substring(0, 70) + "...";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetailsLinkUrl() {
		return detailsLinkUrl;
	}

	public void setDetailsLinkUrl(String detailsLinkUrl) {
		this.detailsLinkUrl = detailsLinkUrl;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
