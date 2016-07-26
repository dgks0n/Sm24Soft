package com.sm24soft.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.util.DateUtil;
import com.sm24soft.entity.AdvertiseImage;
import com.sm24soft.repository.AdvertiseImageRepository;

@Service(IAdvertiseImageService.SERVICE_ID)
public class AdvertiseImageService implements IAdvertiseImageService {

	private AdvertiseImageRepository advertiseImageRepository;
	
	@Autowired
	public AdvertiseImageService(AdvertiseImageRepository advertiseImageRepository) {
		this.advertiseImageRepository = advertiseImageRepository;
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewAdvertiseImage(AdvertiseImage advertiseImage) {
		if (advertiseImage == null) {
			throw new IllegalArgumentException("Object must not be null");
		}
		advertiseImage.setCreatedAt(DateUtil.now());
		advertiseImage.setCreatedUserIdAsDefault();
		advertiseImage.setUpdatedAt(DateUtil.now());
		advertiseImage.setUpdatedUserIdAsDefault();
		
		advertiseImageRepository.save(advertiseImage);
		return advertiseImage.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateAdvertiseImage(AdvertiseImage advertiseImage) {
		if (advertiseImage == null) {
			throw new IllegalArgumentException("Object must not be null");
		}
		
		if (StringUtils.isEmpty(advertiseImage.getId())) {
			throw new ObjectNotFoundException("Not found");
		}
		
		AdvertiseImage oldAdvertiseImage = advertiseImageRepository.findById(advertiseImage.getIdWithPADZero());
		if (oldAdvertiseImage == null) {
			throw new ObjectNotFoundException("Not found");
		}
		oldAdvertiseImage.setAdvertiseTitle(advertiseImage.getAdvertiseTitle());
		oldAdvertiseImage.setAdvertiseUrl(advertiseImage.getAdvertiseUrl());
		oldAdvertiseImage.setAdvertisePath(advertiseImage.getAdvertisePath());
		oldAdvertiseImage.setUpdatedAt(DateUtil.now());
		oldAdvertiseImage.setUpdatedUserIdAsDefault();
		
		advertiseImageRepository.update(oldAdvertiseImage);
		return oldAdvertiseImage.getIdWithPADZero();
	}
	
	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateAdvertiseImage(String id, File file) {
		if (StringUtils.isEmpty(id)) {
			throw new ObjectNotFoundException("Not found");
		}
		
		AdvertiseImage oldAdvertiseImage = advertiseImageRepository.findById(id);
		if (oldAdvertiseImage == null) {
			throw new ObjectNotFoundException("Not found");
		}
		oldAdvertiseImage.setAdvertiseTitle(file.getName());
		oldAdvertiseImage.setAdvertisePath(file.getPath());
		oldAdvertiseImage.setUpdatedAt(DateUtil.now());
		oldAdvertiseImage.setUpdatedUserIdAsDefault();
		
		advertiseImageRepository.update(oldAdvertiseImage);
		return oldAdvertiseImage.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("ID must not be null and empty");
		}
		advertiseImageRepository.deleteById(id);
	}

	@Override
	public AdvertiseImage findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("ID must not be null and empty");
		}
		return advertiseImageRepository.findById(id);
	}

	@Override
	public List<AdvertiseImage> findAll() {
		return advertiseImageRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String[] createNewAdvertiseImages(List<AdvertiseImage> listOfAdvertisementImages) {
		if (CollectionUtils.isEmpty(listOfAdvertisementImages)) {
			throw new IllegalArgumentException("The collection is invalid");
		}
		
		advertiseImageRepository.saveListOfAdvertiseImages(listOfAdvertisementImages);
		
		List<String> createdObjectId = new ArrayList<>();
		for (AdvertiseImage temp : listOfAdvertisementImages) {
			createdObjectId.add(temp.getIdWithPADZero());
		}
		return createdObjectId.toArray(new String[createdObjectId.size()]);
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String[] createNewAdvertiseImagesByFiles(List<File> files) {
		if (CollectionUtils.isEmpty(files)) {
			throw new IllegalArgumentException("The collection is invalid");
		}
		
		List<AdvertiseImage> listOfAdvertisementImages = new ArrayList<>();
		for (File file : files) {
			AdvertiseImage advertiseImage = new AdvertiseImage();
			advertiseImage.setAdvertiseTitle(file.getName());
			advertiseImage.setAdvertisePath(file.getPath());
			advertiseImage.setCreatedAt(DateUtil.now());
			advertiseImage.setUpdatedAt(DateUtil.now());
			advertiseImage.setCreatedUserIdAsDefault();
			advertiseImage.setUpdatedUserIdAsDefault();
			listOfAdvertisementImages.add(advertiseImage);
		}
		
		advertiseImageRepository.saveListOfAdvertiseImages(listOfAdvertisementImages);
		
		List<String> createdObjectId = new ArrayList<>();
		for (AdvertiseImage temp : listOfAdvertisementImages) {
			createdObjectId.add(temp.getIdWithPADZero());
		}
		return createdObjectId.toArray(new String[createdObjectId.size()]);
	}

}
