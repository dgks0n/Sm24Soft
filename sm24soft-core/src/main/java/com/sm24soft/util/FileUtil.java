package com.sm24soft.util;

import java.io.File;

import com.sm24soft.common.exception.ResourceException;

public final class FileUtil extends com.sm24soft.common.util.FileUtil {
	
	public static final String DEFAULT_LOGO_FOLDER_NAME = "/logo";
	public static final String DEFAULT_IMAGE_FOLDER_NAME = "/image";
	
	/**
	 * Default item's image folder name
	 */
	public static final String DEFAULT_ITEM_IMAGE_FOLDER_NAME = "/item/image";
	
	public static final String DEFAULT_ADVERTISE_IMAGE_FOLDER_NAME = "/advertise-images";

	public static String getSupplierHomeDirectory(final String supplierEmail) {
		String supplierHomeDir = getHomeDirectory() + File.separator + supplierEmail;
		File homeDir  = new File(supplierHomeDir);
		if (!homeDir.exists()) {
			homeDir.mkdirs();
		}
		return homeDir.getPath();
	}
	
	private static void createSupplierResourceDirectory(final String supplierEmail) {
		String supplierHomeDir = getSupplierHomeDirectory(supplierEmail);
		File logoDir = new File(supplierHomeDir + DEFAULT_LOGO_FOLDER_NAME);
		if (!logoDir.exists()) {
			logoDir.mkdirs();
		}
		File imageDir = new File(supplierHomeDir + DEFAULT_IMAGE_FOLDER_NAME);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
	}
	
	public static String getSupplierLogoPath(final String supplierEmail) throws ResourceException {
		// create resource first
		createSupplierResourceDirectory(supplierEmail);
		
		File temp = new File(getSupplierHomeDirectory(supplierEmail) + DEFAULT_LOGO_FOLDER_NAME);
		if (temp.exists()) {
			return temp.getPath();
		}
		throw new ResourceException("Not found");
	}
	
	public static String getSupplierOperationImagePath(final String supplierEmail) 
			throws ResourceException {
		// create resource first
		createSupplierResourceDirectory(supplierEmail);
		
		File temp = new File(getSupplierHomeDirectory(supplierEmail) + DEFAULT_IMAGE_FOLDER_NAME);
		if (temp.exists()) {
			return temp.getPath();
		}
		throw new ResourceException("Not found");
	}
	
	public static String getSupplierOperationImagePath(final String supplierEmail, final String imageField) 
			throws ResourceException {
		File temp = new File(getSupplierOperationImagePath(supplierEmail) 
				+ File.separator 
				+ imageField);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		return temp.getPath();
	}
	
	public static String getItemImagePath() {
		File imageDir = new File(getHomeDirectory() + DEFAULT_ITEM_IMAGE_FOLDER_NAME);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return imageDir.getPath();
	}
	
	public static String getItemImagePath(final String imageField) {
		File imageDir = new File(getItemImagePath() + File.separator + imageField);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return imageDir.getPath();
	}
	
	public static final String getAdvertiseImagePath() {
		File imageDir = new File(getHomeDirectory() + DEFAULT_ADVERTISE_IMAGE_FOLDER_NAME);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return imageDir.getPath();
	}
	
	public static final String getAdvertiseImagePath(final String imageId) {
		File imageDir = new File(getAdvertiseImagePath() + File.separator + imageId);
		if (!imageDir.exists()) {
			imageDir.mkdirs();
		}
		return imageDir.getPath();
	}
	
}
