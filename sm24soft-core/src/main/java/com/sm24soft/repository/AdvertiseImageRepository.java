package com.sm24soft.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sm24soft.entity.AdvertiseImage;

public interface AdvertiseImageRepository extends CrudRepository<AdvertiseImage, String> {

	void saveListOfAdvertiseImages(@Param("advertiseImages") List<AdvertiseImage> advertiseImages);
	
}
