package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.Image;

public interface ImageRepository extends CrudRepository<Image, String> {
	
	Image findLogoBySupplierId(String id);
	
	List<Image> findOperationImagesBySupplierId(String id);
	
}
