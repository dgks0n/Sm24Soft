/**
 * 
 */
package com.sm24soft.entity;

import java.util.Date;

/**
 * @author sondn
 *
 */
public class Item extends BaseEntity {

	private String pLUCode;
	private String shortName;
	private String fullName;
	private String description;
	private String itemCategoryCode;
	private String warehouseCode;
	private String storeCode;
	private String supplierCode;
	private Date importDate;
	private double price;
	private double salePrice;
	private String unitOfPrice; // VND, USD, ...etc
	private String taxCode;
	private String saleableFlg; // 0 or 1
	private Date manufactureDate;
	private Date expireDate;
	private String color;
	private String size;
	private String unitOfSize;
	private float weight;
	private float totalWeight;
	private String unitOfWeight;
	private String unitOfTotalWeight;
	private String thumbnailUrl;
	private String previewImageUrl;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getpLUCode() {
		return pLUCode;
	}

	public void setpLUCode(String pLUCode) {
		this.pLUCode = pLUCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemCategoryCode() {
		return itemCategoryCode;
	}

	public void setItemCategoryCode(String itemCategoryCode) {
		this.itemCategoryCode = itemCategoryCode;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public String getUnitOfPrice() {
		return unitOfPrice;
	}

	public void setUnitOfPrice(String unitOfPrice) {
		this.unitOfPrice = unitOfPrice;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getSaleableFlg() {
		return saleableFlg;
	}

	public void setSaleableFlg(String saleableFlg) {
		this.saleableFlg = saleableFlg;
	}

	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUnitOfSize() {
		return unitOfSize;
	}

	public void setUnitOfSize(String unitOfSize) {
		this.unitOfSize = unitOfSize;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getUnitOfWeight() {
		return unitOfWeight;
	}

	public void setUnitOfWeight(String unitOfWeight) {
		this.unitOfWeight = unitOfWeight;
	}

	public String getUnitOfTotalWeight() {
		return unitOfTotalWeight;
	}

	public void setUnitOfTotalWeight(String unitOfTotalWeight) {
		this.unitOfTotalWeight = unitOfTotalWeight;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getPreviewImageUrl() {
		return previewImageUrl;
	}

	public void setPreviewImageUrl(String previewImageUrl) {
		this.previewImageUrl = previewImageUrl;
	}

}
