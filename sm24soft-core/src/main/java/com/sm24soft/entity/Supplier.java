package com.sm24soft.entity;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.sm24soft.common.util.StringFormatUtil;

public class Supplier extends BaseEntity {

	private String companyName;
	private String companyTradingName;
	private String address1;
	private String address2;
	private String email;
	private String telephoneNumber1;
	private String telephoneNumber2;
	private String telephoneNumber3;
	private String faxNumber1;
	private String faxNumber2;
	private String faxNumber3;
	
	/**
	 * Điểm tích luỹ của Trang trại.
	 * 
	 * TH có nhiều sản phẩm của Trang Trại được bán, điều này đồng nghĩa
	 * với việc Trang Trại sẽ được cộng điểm.
	 */
	private int loyaltyAccumulatedPoint = 0;
	
	/**
	 * Người đại diện pháp lý của Trang Trại 
	 */
	private RepresentativeOrContactPerson representativePerson;
	
	/**
	 * Người liên lạc
	 */
	private RepresentativeOrContactPerson contactPerson;
	
	private List<ItemCategory> listOfItemCategories;
	private List<Item> listOfItems;
	
	// Added more information by SONDN
	// Modified on 2016-11-07
	
	/**
	 * Danh sách tin tức của Trang trại & Nhà cung cấp 
	 */
	private List<News> listOfNews;
	private Image logoUrl;
	
	/**
	 * Should use Text Editor for this field at client side
	 */
	private String description;
	private List<Image> listOfImages;
	private List<CertificationStandard> listOfCertificationStandards;

	public Supplier() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTradingName() {
		return companyTradingName;
	}

	public void setCompanyTradingName(String companyTradingName) {
		this.companyTradingName = companyTradingName;
	}

	public RepresentativeOrContactPerson getRepresentativePerson() {
		return representativePerson;
	}

	public void setRepresentativePerson(RepresentativeOrContactPerson representativePerson) {
		this.representativePerson = representativePerson;
	}

	public RepresentativeOrContactPerson getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(RepresentativeOrContactPerson contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephoneNumber1() {
		return telephoneNumber1;
	}

	public void setTelephoneNumber1(String telephoneNumber1) {
		this.telephoneNumber1 = telephoneNumber1;
	}

	public String getTelephoneNumber2() {
		return telephoneNumber2;
	}

	public void setTelephoneNumber2(String telephoneNumber2) {
		this.telephoneNumber2 = telephoneNumber2;
	}

	public String getTelephoneNumber3() {
		return telephoneNumber3;
	}

	public void setTelephoneNumber3(String telephoneNumber3) {
		this.telephoneNumber3 = telephoneNumber3;
	}

	public String getFaxNumber1() {
		return faxNumber1;
	}

	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}

	public String getFaxNumber2() {
		return faxNumber2;
	}

	public void setFaxNumber2(String faxNumber2) {
		this.faxNumber2 = faxNumber2;
	}

	public String getFaxNumber3() {
		return faxNumber3;
	}

	public void setFaxNumber3(String faxNumber3) {
		this.faxNumber3 = faxNumber3;
	}
	
	public int getLoyaltyAccumulatedPoint() {
		return loyaltyAccumulatedPoint;
	}

	public void setLoyaltyAccumulatedPoint(int loyaltyAccumulatedPoint) {
		this.loyaltyAccumulatedPoint = loyaltyAccumulatedPoint;
	}
	
	public List<ItemCategory> getListOfItemCategories() {
		return listOfItemCategories;
	}

	public void setListOfItemCategories(List<ItemCategory> listOfItemCategories) {
		this.listOfItemCategories = listOfItemCategories;
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}

	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(4, getId());
	}
	
	public List<News> getListOfNews() {
		return listOfNews;
	}

	public void setListOfNews(List<News> listOfNews) {
		this.listOfNews = listOfNews;
	}

	public Image getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(Image logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Image> getListOfImages() {
		return listOfImages;
	}

	public void setListOfImages(List<Image> listOfImages) {
		this.listOfImages = listOfImages;
	}

	public List<CertificationStandard> getListOfCertificationStandards() {
		return listOfCertificationStandards;
	}

	public void setListOfCertificationStandards(List<CertificationStandard> listOfCertificationStandards) {
		this.listOfCertificationStandards = listOfCertificationStandards;
	}
	
	/**
	 * Get operation image for the supplier
	 * by using index of field ID
	 * 
	 * @param index
	 * @return
	 */
	public Image getOperationImageByIndex(int index) {
		if (CollectionUtils.isEmpty(getListOfImages())) {
			return null;
		}
		int size = getListOfImages().size();
		if (index > size) {
			return null;
		}
		return getListOfImages().get(index - 1);
	}
	
	public Image getOperationImageByFieldId(String fieldId) {
		if (CollectionUtils.isEmpty(getListOfImages())) {
			return null;
		}
		
		if (StringUtils.isEmpty(fieldId)) {
			return null;
		}
		
		for (Image image : listOfImages) {
			if (null != image && fieldId.equals(image.getImageFieldId())) {
				return image;
			}
		}
		return null;
	}
 
}
