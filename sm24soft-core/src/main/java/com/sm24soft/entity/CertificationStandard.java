package com.sm24soft.entity;

public class CertificationStandard extends BaseEntity {

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
