package com.sm24soft.entity;

import java.util.Date;
import java.util.List;

public class Invoice extends BaseEntity {

	private String name;
	private String invoiceNumber;
	private String templateNumber;
	private Date dateOfIssue;
	private String storeCode;
	private String customerCode;
	private List<ItemDetail> itemDetails;
	private String originInvoiceNumber;
	private String returnedInvoiceFlg; // 0 = Normal Sale, 1 = Return Invoice
	private double totalPriceBeforeVAT;
	private String valueAddedTax; // 10%
	private double totalValueAddedTax; // 10%
	private double totalPriceAfterVAT;
	private Date dateOfSale;
	private String sellerCode;
	private String paymentCode;

	public Invoice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}

	public Date getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public String getOriginInvoiceNumber() {
		return originInvoiceNumber;
	}

	public void setOriginInvoiceNumber(String originInvoiceNumber) {
		this.originInvoiceNumber = originInvoiceNumber;
	}

	public String getReturnedInvoiceFlg() {
		return returnedInvoiceFlg;
	}

	public void setReturnedInvoiceFlg(String returnedInvoiceFlg) {
		this.returnedInvoiceFlg = returnedInvoiceFlg;
	}

	public double getTotalPriceBeforeVAT() {
		return totalPriceBeforeVAT;
	}

	public void setTotalPriceBeforeVAT(double totalPriceBeforeVAT) {
		this.totalPriceBeforeVAT = totalPriceBeforeVAT;
	}

	public String getValueAddedTax() {
		return valueAddedTax;
	}

	public void setValueAddedTax(String valueAddedTax) {
		this.valueAddedTax = valueAddedTax;
	}

	public double getTotalValueAddedTax() {
		return totalValueAddedTax;
	}

	public void setTotalValueAddedTax(double totalValueAddedTax) {
		this.totalValueAddedTax = totalValueAddedTax;
	}

	public double getTotalPriceAfterVAT() {
		return totalPriceAfterVAT;
	}

	public void setTotalPriceAfterVAT(double totalPriceAfterVAT) {
		this.totalPriceAfterVAT = totalPriceAfterVAT;
	}

	public Date getDateOfSale() {
		return dateOfSale;
	}

	public void setDateOfSale(Date dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

}
