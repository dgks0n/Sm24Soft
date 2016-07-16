package com.sm24soft.entity;

import com.sm24soft.common.util.StringFormatUtil;

public class Image extends BaseEntity {
	
	public static final String LOGO_TYPE = "0";
	public static final String OPERATION_IMAGE_TYPE = "1";
	public static final String ITEM_TYPE = "2";
	
	/**
	 * The IMAGE table have serveral fields:
	 * 
	 * id
	 * image_field_id
	 * image_url
	 * kind_of_image
	 * supplier_id
	 * supplier_email_address
	 * delete_flg
	 * created_at
	 * created_user_id
	 * updated_at
	 * updated_user_id
	 */
	private String imageUrl;
	
	/**
	 * LOGO:
	 * 		LOGO
	 * OPERATION IMAGE:
	 * 		OPERATION_IMAGE_01
	 * 		OPERATION_IMAGE_02
	 * 		OPERATION_IMAGE_03
	 * 		OPERATION_IMAGE_04
	 * 		OPERATION_IMAGE_05
	 * 		OPERATION_IMAGE_06
	 * 		OPERATION_IMAGE_07
	 * 		OPERATION_IMAGE_08
	 * 		OPERATION_IMAGE_09
	 * 		OPERATION_IMAGE_10
	 */
	private String imageFieldId;
	
	/**
	 * 0: LOGO
	 * 1: OPERATION_IMAGE
	 * 2: ITEM
	 */
	private String kindOfImage = "0";
	private Supplier supplier;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getImageFieldId() {
		return imageFieldId;
	}

	public void setImageFieldId(String imageFieldId) {
		this.imageFieldId = imageFieldId;
	}

	public String getKindOfImage() {
		return kindOfImage;
	}

	public void setKindOfImage(String kindOfImage) {
		this.kindOfImage = kindOfImage;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(11, getId());
	}
	
}
