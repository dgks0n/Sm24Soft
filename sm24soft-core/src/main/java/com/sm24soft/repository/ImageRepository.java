package com.sm24soft.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm24soft.entity.Image;

public interface ImageRepository extends CrudRepository<Image, String> {
	
	Image findLogoBySupplierEmailAddress(@Param("emailAddress") String emailAddress, 
			@Param("imageFieldId") String imageFieldId);
	
	Image findLogoBySupplierEmailAddressAndDefaultFieldId(String emailAddress);
	
	Image findOperationImageBySupplierEmailAddressAndFieldId(@Param("emailAddress") String emailAddress,
			@Param("imageFieldId") String imageFieldId);
	
	List<Image> findOperationImagesBySupplierEmailAddress(String emailAddress);
	
}
