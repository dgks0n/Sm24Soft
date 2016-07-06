/**
 * 
 */
package com.sm24soft.entity;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sondn
 *
 */
public class Item extends BaseEntity {

	private String pLUCode1;
	private String pLUCode2;
	private String pLUCode3;
	private String shortName;
	private String fullName;
	private String description;
	
	/*
	 * Belong to a item-category
	 */
	private ItemCategory itemCategory;
	
	/*
	 * Belong to a store
	 */
	private Store store;
	
	/*
	 * Belong to a supplier
	 */
	private Supplier supplier;
	
	private Date importDate;
	private double price;
	private double salePrice;
	private double oldSalePrice;
	
	/*
	 * VND, USD, ...etc
	 */
	private String unitOfSalePrice;
	private float discount;
	private String unitOfDiscount;
	
	/*
	 * NULL, 0: NON_TAX
	 */
	private Tax tax;
	
	/*
	 * 0: NOT_FOR_SALE, 1: SALE
	 */
	private String saleableFlg;
	
	/*
	 * Ngay SX
	 */
	private Date manufactureDate;
	
	/*
	 * Ngay HH
	 */
	private Date expireDate;
	
	/*
	 * Can nang / 1 kg
	 */
	private float weight;
	
	/*
	 * Danh sach can nang / 1 box. 
	 * 
	 * Ex: [{size: "small", weight: 0.5}, {size: "middle", weight: 1}
	 */
	private String weightOfOneBox;
	
	/*
	 * Tong so can nang
	 */
	private float totalWeight;
	
	/*
	 * Tong so can nang con lai sau khi da ban
	 */
	private float totalRemainingWeightAfterSell;
	private String unitOfWeight;
	private String thumbnailUrl;
	private String previewImageUrl;

	public Item() {
		super();
	}

	public String getpLUCode1() {
		return pLUCode1;
	}

	public void setpLUCode1(String pLUCode1) {
		this.pLUCode1 = pLUCode1;
	}

	public String getpLUCode2() {
		return pLUCode2;
	}

	public void setpLUCode2(String pLUCode2) {
		this.pLUCode2 = pLUCode2;
	}

	public String getpLUCode3() {
		return pLUCode3;
	}

	public void setpLUCode3(String pLUCode3) {
		this.pLUCode3 = pLUCode3;
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

	public ItemCategory getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(ItemCategory itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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

	public double getOldSalePrice() {
		return oldSalePrice;
	}

	public void setOldSalePrice(double oldSalePrice) {
		this.oldSalePrice = oldSalePrice;
	}

	public String getUnitOfSalePrice() {
		return unitOfSalePrice;
	}

	public void setUnitOfSalePrice(String unitOfSalePrice) {
		this.unitOfSalePrice = unitOfSalePrice;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public String getUnitOfDiscount() {
		return unitOfDiscount;
	}

	public void setUnitOfDiscount(String unitOfDiscount) {
		this.unitOfDiscount = unitOfDiscount;
	}

	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
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

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public List<BoxReference> getWeightOfOneBox() {
		ObjectMapper mapper = new ObjectMapper();
		List<BoxReference> boxes = null;
		try {
			boxes = mapper.readValue(weightOfOneBox, new TypeReference<List<BoxReference>>(){});
		} catch (IOException ex) {
			// TODO:
		}
		return boxes;
	}

	public void setWeightOfOneBox(String weightOfOneBox) {
		this.weightOfOneBox = weightOfOneBox;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}

	public float getTotalRemainingWeightAfterSell() {
		return totalRemainingWeightAfterSell;
	}

	public void setTotalRemainingWeightAfterSell(float totalRemainingWeightAfterSell) {
		this.totalRemainingWeightAfterSell = totalRemainingWeightAfterSell;
	}

	public String getUnitOfWeight() {
		return unitOfWeight;
	}

	public void setUnitOfWeight(String unitOfWeight) {
		this.unitOfWeight = unitOfWeight;
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
