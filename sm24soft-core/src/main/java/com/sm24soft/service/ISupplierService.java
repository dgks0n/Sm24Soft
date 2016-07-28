package com.sm24soft.service;

import java.io.File;
import java.util.List;

import com.sm24soft.entity.Image.ImageType;
import com.sm24soft.entity.Supplier;

public interface ISupplierService extends IService {
	
	public static final String SERVICE_ID = "supplierService";

	String createNewSupplier(Supplier supplier);
	
	String updateSupplier(Supplier supplier);
	
	String uploadLogo(String email, String existingLogoId, ImageType imageType, File logoFile);
	
	String uploadOperationImage(String email, String existingOperImageId, ImageType imageType, File imageFile);
	
	Supplier findById(String id);
	
	List<Supplier> findAllSuppliers();
	
	/**
	 * Took out the list of top farm for the given a number limitation.
	 * 
	 * Example:
	 * 		limitation = 5 => get five top farms
	 * 
	 * @param limitation
	 * @return
	 */
	List<Supplier> findTopSuppliersByLimitation(int limitation);
	
	void deleteById(String id);
	
}
