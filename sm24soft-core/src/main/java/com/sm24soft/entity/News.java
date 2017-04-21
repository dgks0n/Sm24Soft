package com.sm24soft.entity;

public class News extends BaseEntity {

	private String linkUrl;
	private String description;
	
	/**
	 * Referent to parent object
	 */
	private Supplier supplier;

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
