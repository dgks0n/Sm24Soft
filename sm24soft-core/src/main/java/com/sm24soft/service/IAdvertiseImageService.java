package com.sm24soft.service;

import java.io.File;
import java.util.List;

import com.sm24soft.entity.AdvertiseImage;

public interface IAdvertiseImageService extends IService {

	static final String SERVICE_ID = "advertiseImageService";
	
	String createNewAdvertiseImage(AdvertiseImage advertiseImage);
	
	String[] createNewAdvertiseImagesByFiles(List<File> files);
	
	String[] createNewAdvertiseImages(List<AdvertiseImage> listOfAdvertisementImages);
	
	String updateAdvertiseImage(AdvertiseImage advertiseImage);
	
	String updateAdvertiseImage(String id, File file);
	
	void deleteById(String id);
	
	AdvertiseImage findById(String id);
	
	List<AdvertiseImage> findAll();
}
