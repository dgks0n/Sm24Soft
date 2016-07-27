package com.sm24soft.entity;

import java.io.File;

import com.sm24soft.common.util.StringFormatUtil;

public class Image extends BaseEntity {
	
	public enum ImageType {
		LOGO("0"), // Supplier's logo
		OPERATION_IMAGE("1"), // Supplier's operation images
		ITEM("2"), // Item's representative images
		
		ITEM_THUMBNAIL("3"); // Item's thumbnail, it's first representative image
		
		private String typeCode;
		
		ImageType(String typeCode) {
			this.typeCode = typeCode;
		}
		
		public String value() {
			return typeCode;
		}
	}

	/**
	 * The IMAGE table have several fields:
	 * 
	 * id 
	 * absolute_path 
	 * caption 
	 * type: 0, 1, 2
	 * supplier_id
	 * item_id 
	 * delete_flg 
	 * created_at 
	 * created_user_id
	 * updated_at 
	 * updated_user_id
	 */
	private String absolutePath;

	private String caption;
	
	private String type;

	private Supplier supplier;
	
	private Item item;

	/**
	 * Return original file
	 * 
	 * NOTE: It's necessary to get original file
	 * 
	 * @return
	 */
	public File getOriginalFile() {
		return new File(getAbsolutePath());
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String getIdWithPADZero() {
		return StringFormatUtil.formatString(12, getId());
	}

}
