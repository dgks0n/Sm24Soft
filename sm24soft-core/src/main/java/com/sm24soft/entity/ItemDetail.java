package com.sm24soft.entity;

public class ItemDetail extends BaseEntity {

	private Item purchasedItem; // Original sell item
	private String unitOfSale;
	private double price;
	private int numberOfItem;
	private double totalPrice;
	private String unitOfTotalPrice;
	
	private Invoice originalInvoice;
	
	public ItemDetail() {
		super();
	}

	public Item getPurchasedItem() {
		return purchasedItem;
	}

	public void setPurchasedItem(Item purchasedItem) {
		this.purchasedItem = purchasedItem;
	}

	public String getUnitOfSale() {
		return unitOfSale;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumberOfItem() {
		return numberOfItem;
	}

	public void setNumberOfItem(int numberOfItem) {
		this.numberOfItem = numberOfItem;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUnitOfTotalPrice() {
		return unitOfTotalPrice;
	}

	public void setUnitOfTotalPrice(String unitOfTotalPrice) {
		this.unitOfTotalPrice = unitOfTotalPrice;
	}

	public Invoice getOriginalInvoice() {
		return originalInvoice;
	}

	public void setOriginalInvoice(Invoice originalInvoice) {
		this.originalInvoice = originalInvoice;
	}

}
