/**
 * 
 */
package com.sm24soft.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.sm24soft.common.util.DateUtil;

/**
 * This is important enity.
 * 
 * @author sondn
 */
public class Item extends BaseEntity {
	
	public static final String DEFAULT_UNIT_OF_SALE = "vnđ";
	public static final String DEFAULT_UNIT_OF_DISCOUNT = "vnđ";
	public static final String DEFAULT_UNIT_OF_WEIGHT = "kg";
	
	public static final String DEFAULT_SALEABLE_FLAG = "1";
	
	/**
	 * NOTE: Formula for how to calculate price of an item as below described
	 * 
	 * ( salePrice / weight ) * weightOfOneBox = price for one box.
	 * Example:
	 * 		(150000 / 1) * 3 = (150.000VND / 1kg) * 3kg (1box) = 450000VND/1box
	 * 
	 * Dien giai:
	 * -----------
	 * 
	 * Gia tien cua san pham nay la 150.000VND cho 1kg. Tuy nhien, san pham nay duoc
	 * dong theo HOP (box) va HOP nay co trong luong 3kg. Nhu vay gia tien cua mot
	 * HOP san pham nay la 150.000 * 3 = 450.000VND.
	 * 
	 * Neu khach hang muon mua 3 hoac 4 HOP (box) thi gia tien se la:
	 * Gia tien 1 HOP (box) * so luong HOP (box) = ???
	 * 
	 */

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
	 * Belong to a store (optional)
	 * 
	 * Default is NULL. 
	 */
	private Store store;
	
	/*
	 * Belong to a supplier (MUST)
	 */
	private Supplier supplier;
	
	/*
	 * Ngay nhap
	 */
	private Date importDate;
	
	/*
	 * Gia nhap tren 1 don vi (NOTE: Luon Luon la 1 don vi).
	 * 
	 * Ex: 140000 VND/1kg
	 */
	private double price = 0;
	
	/*
	 * Gia ban hien tai tren 1 don vi.
	 * 
	 * Ex: 150000 VND/1kg
	 */
	private double salePrice = 0;
	
	/*
	 * Gia ban cu tren 1 don vi kg.
	 * 
	 * Ex: 160000 VND/1kg
	 * 
	 * NOTE: Gia nay se duoc tinh dua tren % chiet khau
	 */
	private double oldSalePrice = 0;
	
	/*
	 * Don vi cua gia ban tren 1 don vi.
	 * Ex: VND (default)
	 */
	private String unitOfSalePrice;
	
	/*
	 * Chiet khau
	 */
	private float discount = 0;
	
	/*
	 * Don vi cua chiet khau
	 */
	private String unitOfDiscount;
	
	/*
	 * NULL, 0: NON_TAX
	 * 
	 * Default is NULL (NON_TAX)
	 */
	private Tax tax;
	
	/*
	 * 0: NOT_FOR_SALE, 1: SALE
	 */
	private String saleableFlg = "1";
	
	/*
	 * Ngay SX
	 */
	private Date manufactureDate = DateUtil.now();
	
	/*
	 * Ngay HH
	 */
	private Date expireDate;
	
	/*
	 * Can nang / 1 kg.
	 * 
	 * NOTE: Default is 1, su dung don vi nay de tinh tien.
	 */
	private float weightOfSalePrice = 1;
	
	/*
	 * Can nang cua box.
	 * Ex: box A: 0.5, box B: 1kg, box C: 3kg, box D: 5kg
	 * 
	 * Default is 1.
	 */
	private float weightOfOneBox = 1;
	
	/*
	 * Don vi cua box 
	 */
	private String unitOfOneBox;
	
	/*
	 * Tong so can nang cua san pham nay.
	 * 
	 * Ex: 100
	 */
	private float totalWeight;
	
	/*
	 * Tong so can nang con lai sau khi da ban.
	 * 
	 * Ex: 10
	 */
	private float totalRemainingWeightAfterSell;
	
	/*
	 * Don vi khoi luong.
	 * 
	 * Default is Kg.
	 */
	private String unitOfWeight = "kg";
	
	/*
	 * Hinh anh dai dien
	 */
	private Image thumbnail;
	
	/*
	 * Hinh anh hien thi
	 */
	private Image previewImage1;
	private Image previewImage2;
	private Image previewImage3;

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
		if (StringUtils.isEmpty(description)) {
			return description;
		}
		return description.trim();
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
	
	public float getWeightOfSalePrice() {
		return weightOfSalePrice;
	}

	public void setWeightOfSalePrice(float weightOfSalePrice) {
		this.weightOfSalePrice = weightOfSalePrice;
	}

	public float getWeightOfOneBox() {
		return weightOfOneBox;
	}
	
	public void setWeightOfOneBox(float weightOfOneBox) {
		this.weightOfOneBox = weightOfOneBox;
	}

	public String getUnitOfOneBox() {
		return unitOfOneBox;
	}

	public void setUnitOfOneBox(String unitOfOneBox) {
		this.unitOfOneBox = unitOfOneBox;
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
	
	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Image getPreviewImage1() {
		return previewImage1;
	}

	public void setPreviewImage1(Image previewImage1) {
		this.previewImage1 = previewImage1;
	}

	public Image getPreviewImage2() {
		return previewImage2;
	}

	public void setPreviewImage2(Image previewImage2) {
		this.previewImage2 = previewImage2;
	}

	public Image getPreviewImage3() {
		return previewImage3;
	}

	public void setPreviewImage3(Image previewImage3) {
		this.previewImage3 = previewImage3;
	}

	public String getActualStatusAsString() {
		StringBuilder builder = new StringBuilder("<button type=\"button\" class=\"btn ");
		if (getTotalRemainingWeightAfterSell() > 0) {
			builder.append("btn-success btn-xs\">Còn hàng");
		} else {
			builder.append("btn-warning btn-xs\">Hết hàng");
		}
		builder.append("</button>");
		return builder.toString();
	}
	
}
