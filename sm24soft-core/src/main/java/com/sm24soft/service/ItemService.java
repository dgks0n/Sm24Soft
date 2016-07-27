package com.sm24soft.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.util.DateUtil;
import com.sm24soft.common.util.StringFormatUtil;
import com.sm24soft.entity.BaseEntity.EntityStatus;
import com.sm24soft.entity.Image;
import com.sm24soft.entity.Image.ImageType;
import com.sm24soft.entity.Item;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.ImageRepository;
import com.sm24soft.repository.ItemRepository;
import com.sm24soft.repository.SupplierRepository;
import com.sm24soft.util.ImageResizeUtil;

@Service(IItemService.SERVICE_ID)
public class ItemService implements IItemService {

	private ItemRepository itemRepository;
	
	private ImageRepository imageRepository;
	
	private SupplierRepository supplierRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository, ImageRepository imageRepository,
			SupplierRepository supplierRepository) {
		this.itemRepository = itemRepository;
		this.imageRepository = imageRepository;
		this.supplierRepository = supplierRepository;
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewItem(Item item) throws IOException {
		if (item == null) {
			throw new IllegalArgumentException("Object is NULL");
		}
		
		Supplier supplier = supplierRepository.findById(item.getSupplier().getIdWithPADZero());
		if (supplier == null) {
			throw new ObjectNotFoundException("Cannot found any supplier");
		}
		
		// ===============================================================
		// Create item
		// ===============================================================
		if (StringUtils.isEmpty(item.getFullName())) {
			item.setFullName(item.getShortName());
		}
		item.setPrice(item.getSalePrice());
		
		// Neu ti le chiet khau (C) > 0 thi can phai tinh toan gia ban: 
		// gia ban thuc te (A) = gia ban (B) - chiet khau (C)
		// Sau do cap nhat gia truoc khi chiet khau (B) cho gia cu, gia moi se la
		// gia ban thuc te (A).
		if (item.getDiscount() > 0) {
			item.setOldSalePrice(item.getSalePrice());
			item.setSalePrice(item.getSalePrice() - item.getDiscount());
		}
		item.setTotalRemainingWeightAfterSell(item.getTotalWeight());
		
		Image prevImage1 = item.getPreviewImage1();
		if (StringUtils.isEmpty(prevImage1.getId())) {
			throw new IllegalArgumentException("The first preview image must not be null");
		}
		
		Image prevImage2 = item.getPreviewImage2();
		if (StringUtils.isEmpty(prevImage2.getId())) {
			item.setPreviewImage2(null);
		}
		
		Image prevImage3 = item.getPreviewImage3();
		if (StringUtils.isEmpty(prevImage3.getId())) {
			item.setPreviewImage3(null);
		}
		
		item.setDeleteFlg(EntityStatus.ACTIVE.value());
		item.setCreatedAt(DateUtil.now());
		item.setCreatedUserIdAsDefault();
		item.setUpdatedAt(DateUtil.now());
		item.setUpdatedUserIdAsDefault();
		
		itemRepository.save(item);
		
		// ===============================================================
		// Updating preview image for each item
		// ===============================================================
		Image prevImage = upgradeInfoForPreviewImage(prevImage1.getId(), item, supplier);
		if (StringUtils.isEmpty(prevImage.getId())) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		upgradeInfoForPreviewImage(prevImage2.getId(), item, supplier);
		upgradeInfoForPreviewImage(prevImage3.getId(), item, supplier);
		
		// ===============================================================
		// Create item's thumbnail
		// ===============================================================
		File originFile = prevImage.getOriginalFile();
		String destFileName = ImageResizeUtil.PREFIX_HYPHEN_THUMBNAIL + originFile.getName();
		
		File destFile = new File(originFile.getParent(), destFileName);
		ImageResizeUtil.resizeUsingJavaAlgo(prevImage.getAbsolutePath(), destFile, 600, 450);
		
		Image thumbnail = new Image();
		thumbnail.setAbsolutePath(destFile.getPath());
		thumbnail.setCaption(destFile.getName());
		thumbnail.setType(ImageType.ITEM_THUMBNAIL.value());
		thumbnail.setSupplier(supplier);
		thumbnail.setItem(item);
		thumbnail.setDeleteFlg(EntityStatus.ACTIVE.value());
		thumbnail.setCreatedAt(DateUtil.now());
		thumbnail.setCreatedUserIdAsDefault();
		thumbnail.setUpdatedAt(DateUtil.now());
		thumbnail.setUpdatedUserIdAsDefault();
		
		imageRepository.save(thumbnail);
		
		// ===============================================================
		// Update item
		// ===============================================================
		item.setThumbnail(thumbnail);
		itemRepository.update(item);
		
		return item.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateItem(Item item) throws IOException {
		if (StringUtils.isEmpty(item.getId())) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		
		Item tempItem = getCopyingOrUpdatingItem(item.getId(), item);
		itemRepository.update(tempItem);
		return tempItem.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Image uploadPreviewOrThumbnailImageForEachItem(final ImageType imageType, final File file) {
		if (file == null || !file.exists()) {
			throw new IllegalArgumentException("File does not exist");
		}
		
		Image image = new Image();
		image.setAbsolutePath(file.getPath());
		image.setCaption(file.getName());
		image.setType(imageType.value());
		image.setSupplier(new Supplier());
		image.setItem(new Item());
		image.setDeleteFlg(EntityStatus.NON_ACTIVE.value());
		image.setCreatedAt(DateUtil.now());
		image.setCreatedUserIdAsDefault();
		image.setUpdatedAt(DateUtil.now());
		image.setUpdatedUserIdAsDefault();
		
		imageRepository.save(image);
		return image;
	}

	@Override
	public List<Item> findAllBySupplierName(String supplierName) {
		String searchKeyword = StringFormatUtil.appendPercentToLeftAndRight(supplierName);
		return itemRepository.findAllBySupplierName(searchKeyword);
	}

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The id must not be null and empty");
		}
		Item item = itemRepository.findById(id);
		if (item == null) {
			throw new ObjectNotFoundException("Not found");
		}
		return item;
	}

	@Override
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		itemRepository.deleteById(id);
	}

	@Override
	public String copyItemFromOtherItem(String oldItemId, Item newItem) throws IOException {
		if (StringUtils.isEmpty(oldItemId)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		
		Item tempItem = getCopyingOrUpdatingItem(oldItemId, newItem);
		itemRepository.save(tempItem);
		return tempItem.getIdWithPADZero();
	}
	
	protected Image upgradeInfoForPreviewImage(String imageId, Item item, Supplier supplier) {
		if (StringUtils.isEmpty(imageId)) {
			return new Image();
		}
		
		Image image = imageRepository.findById(imageId);
		if (image == null) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		image.setSupplier(supplier);
		image.setItem(item);
		image.setDeleteFlg(EntityStatus.ACTIVE.value());
		image.setUpdatedAt(DateUtil.now());
		image.setUpdatedUserIdAsDefault();
		
		imageRepository.update(image);
		return image;
	}
	
	protected Item getCopyingOrUpdatingItem(String oldItemId, Item newItem) throws IOException {
		Item oldItem = itemRepository.findById(oldItemId);
		if (oldItem == null) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		oldItem.setShortName(newItem.getShortName());
		oldItem.setFullName(newItem.getFullName());
		if (StringUtils.isEmpty(newItem.getFullName())) {
			oldItem.setFullName(newItem.getShortName());
		}
		oldItem.setItemCategory(newItem.getItemCategory());
		
		String supplierId = newItem.getSupplier().getId();
		Supplier supplier = supplierRepository.findById(supplierId);
		if (supplier == null) {
			throw new ObjectNotFoundException("Cannot found any supplier matches with supplier's ID: " + supplierId);
		}
		
		Image prevImage1 = newItem.getPreviewImage1();
		if (StringUtils.isNotEmpty(prevImage1.getId())) {
			prevImage1 = upgradeInfoForPreviewImage(prevImage1.getId(), oldItem, supplier);
			if (StringUtils.isEmpty(prevImage1.getId())) {
				throw new ObjectNotFoundException("Object not found");
			}
			
			oldItem.setPreviewImage1(prevImage1);
			
			// Create item's thumbnail
			File originFile = prevImage1.getOriginalFile();
			String destFileName = ImageResizeUtil.PREFIX_HYPHEN_THUMBNAIL + originFile.getName();
			
			File destFile = new File(originFile.getParent(), destFileName);
			ImageResizeUtil.resizeUsingJavaAlgo(prevImage1.getAbsolutePath(), destFile, 600, 450);
			
			Image thumbnail = new Image();
			thumbnail.setAbsolutePath(destFile.getPath());
			thumbnail.setCaption(destFile.getName());
			thumbnail.setType(ImageType.ITEM_THUMBNAIL.value());
			thumbnail.setSupplier(supplier);
			thumbnail.setItem(oldItem);
			thumbnail.setDeleteFlg(EntityStatus.ACTIVE.value());
			thumbnail.setCreatedAt(DateUtil.now());
			thumbnail.setCreatedUserIdAsDefault();
			thumbnail.setUpdatedAt(DateUtil.now());
			thumbnail.setUpdatedUserIdAsDefault();
			
			imageRepository.save(thumbnail);
			oldItem.setThumbnail(thumbnail);
		}
		
		Image prevImage2 = newItem.getPreviewImage2();
		if (StringUtils.isNotEmpty(prevImage2.getId())) {
			prevImage2 = upgradeInfoForPreviewImage(prevImage2.getId(), oldItem, supplier);
			oldItem.setPreviewImage2(prevImage2);
		}
		
		Image prevImage3 = newItem.getPreviewImage3();
		if (StringUtils.isNotEmpty(prevImage3.getId())) {
			prevImage3 = upgradeInfoForPreviewImage(prevImage3.getId(), oldItem, supplier);
			oldItem.setPreviewImage2(prevImage3);
		}
		
		oldItem.setPrice(newItem.getSalePrice());
		oldItem.setDiscount(newItem.getDiscount());
		
		// Neu ti le chiet khau (C) > 0 thi can phai tinh toan gia ban: 
		// gia ban thuc te (A) = gia ban (B) - chiet khau (C)
		// Sau do cap nhat gia truoc khi chiet khau (B) cho gia cu, gia moi se la
		// gia ban thuc te (A).
		if (newItem.getDiscount() > 0) {
			oldItem.setSalePrice(newItem.getSalePrice() - newItem.getDiscount());
		} else {
			oldItem.setSalePrice(newItem.getSalePrice());
		}
		oldItem.setTotalWeight(newItem.getTotalWeight());
		oldItem.setTotalRemainingWeightAfterSell(newItem.getTotalWeight());
		oldItem.setDescription(newItem.getDescription());
		oldItem.setUpdatedAt(DateUtil.now());
		oldItem.setUpdatedUserIdAsDefault();
		return oldItem;
	}

}
