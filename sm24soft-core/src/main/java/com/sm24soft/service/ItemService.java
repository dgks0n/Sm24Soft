package com.sm24soft.service;

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
import com.sm24soft.entity.Image;
import com.sm24soft.entity.Item;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.ImageRepository;
import com.sm24soft.repository.ItemRepository;
import com.sm24soft.repository.SupplierRepository;

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
	public String createNewItem(Item item) {
		/* Data example for item:
		 * 
		 * {
		 * 	"supplier":{
		 * 		"id":"0009"
		 * 	},
		 * 	"itemCategory":{
		 * 		"id":"0003"
		 * 	},
		 * 	"shortName":"Xu hào",
		 * 	"fullName":"",
		 * 	"salePrice":"10000",
		 * 	"unitOfSalePrice":"01",
		 * 	"totalWeight":"50",
		 * 	"unitOfWeight":"01",
		 * 	"weightOfOneBox":"1",
		 *  "unitOfOneBox": "1",
		 * 	"discount":"0",
		 * 	"unitOfDiscount":"01",
		 * 	"description":"",
		 * 	"previewImageUrl1":{
		 * 		"id":""
		 * 	},
		 * 	"previewImageUrl2":{
		 * 		"id":""
		 * 	},
		 * 	"previewImageUrl3":{
		 * 		"id":""
		 * 	}
		 * }
		 */
		if (item == null) {
			throw new IllegalArgumentException("Object is NULL");
		}
		
		// Update email for item's image table 
		String supplierId = item.getSupplier().getId();
		Supplier supplier = supplierRepository.findById(supplierId);
		if (supplier == null) {
			throw new ObjectNotFoundException("Cannot found any supplier matches with supplier's ID: " + supplierId);
		}
		
		Image prevImg1 = upgradeEmailForPreviewImageById(item.getPreviewImage1().getIdWithPADZero(), supplier);
		item.setPreviewImage1(prevImg1);
		item.setThumbnail(prevImg1);
		
		Image prevImg2 = upgradeEmailForPreviewImageById(item.getPreviewImage2().getIdWithPADZero(), supplier);
		item.setPreviewImage2(prevImg2);
		
		Image prevImg3 = upgradeEmailForPreviewImageById(item.getPreviewImage3().getIdWithPADZero(), supplier);
		item.setPreviewImage3(prevImg3);
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
		item.setCreatedAt(DateUtil.now());
		item.setCreatedUserIdAsDefault();
		item.setUpdatedAt(DateUtil.now());
		item.setUpdatedUserIdAsDefault();
		
		itemRepository.save(item);
		return item.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateItem(Item item) {
		if (StringUtils.isEmpty(item.getId())) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		
		Item migratedItem = migrateCopyingOrUpdatingItem(item.getId(), item);
		itemRepository.update(migratedItem);
		return migratedItem.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String uploadImageForItem(final String imageId, final String imageField, 
			final String imageUrl) {
		if (StringUtils.isEmpty(imageField)) {
			throw new IllegalArgumentException("The image field must not be null and empty");
		}
		
		if (StringUtils.isEmpty(imageUrl)) {
			throw new IllegalArgumentException("The image's URL must not be null and empty");
		}
		
		// default supplier is null
		Supplier supplier = new Supplier();
		
		// In-case create new one
		if (StringUtils.isEmpty(imageId)) {
			Image image = new Image();
			image.setImageFieldId(imageField);
			image.setImageUrl(imageUrl);
			image.setKindOfImage(Image.ITEM_TYPE);
			image.setSupplier(supplier);
			image.setCreatedAt(DateUtil.now());
			image.setCreatedUserIdAsDefault();
			image.setUpdatedAt(DateUtil.now());
			image.setUpdatedUserIdAsDefault();
			
			imageRepository.save(image);
			return image.getIdWithPADZero();
		} else { // In-case update
			Image image = imageRepository.findById(imageId);
			if (image == null) {
				throw new ObjectNotFoundException("Object not found");
			}
			
			image.setImageUrl(imageUrl);
			if (image.getSupplier() == null) {
				image.setSupplier(supplier);
			}
			image.setUpdatedAt(DateUtil.now());
			image.setUpdatedUserIdAsDefault();
			
			imageRepository.update(image);
			return image.getIdWithPADZero();
		}
	}

	@Override
	public List<Item> findAllBySupplierName(String supplierName) {
		String searchKeywordPattern = StringFormatUtil.appendPercentToLeftAndRight(supplierName);
		return itemRepository.findAllBySupplierName(searchKeywordPattern);
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
	public String copyItemFromAnotherItem(String originalItemId, Item customizeItem) {
		if (StringUtils.isEmpty(originalItemId)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		
		Item migratedItem = migrateCopyingOrUpdatingItem(originalItemId, customizeItem);
		itemRepository.save(migratedItem);
		return migratedItem.getIdWithPADZero();
	}
	
	/**
	 * Upgrade supplier's information for item's image object
	 * because the email field is necessary for search in the image table.
	 * 
	 * @param imageId
	 * @param supplier
	 */
	private Image upgradeEmailForPreviewImageById(String imageId, Supplier supplier) {
		if (StringUtils.isNotEmpty(imageId)) {
			Image image = imageRepository.findById(imageId);
			if (image != null) {
				image.setSupplier(supplier);
				image.setUpdatedAt(DateUtil.now());
				image.setUpdatedUserIdAsDefault();
				imageRepository.update(image);
				return image;
			}
		}
		return new Image();
	}
	
	/**
	 * Migrate copying or updating item between existing item with new item
	 * (have fews updated fields)
	 * 
	 * @param originItemId
	 * @param newItem
	 * @return
	 */
	private Item migrateCopyingOrUpdatingItem(String originItemId, Item newItem) {
		Item currentItem = itemRepository.findById(originItemId);
		if (currentItem == null) {
			throw new ObjectNotFoundException("Not found");
		}
		
		currentItem.setShortName(newItem.getShortName());
		currentItem.setFullName(newItem.getFullName());
		if (StringUtils.isEmpty(newItem.getFullName())) {
			currentItem.setFullName(newItem.getShortName());
		}
		currentItem.setItemCategory(newItem.getItemCategory());
		// Update email for item's image table 
		String supplierId = newItem.getSupplier().getId();
		Supplier supplier = supplierRepository.findById(supplierId);
		if (supplier == null) {
			throw new ObjectNotFoundException("Cannot found any supplier matches with supplier's ID: " + supplierId);
		}
		
		Image previewImg01 = upgradeEmailForPreviewImageById(newItem.getPreviewImage1().getIdWithPADZero(), supplier);
		if (StringUtils.isNotEmpty(previewImg01.getId())) {
			currentItem.setPreviewImage1(previewImg01);
			currentItem.setThumbnail(previewImg01);
		}
		
		Image previewImg02 = upgradeEmailForPreviewImageById(newItem.getPreviewImage2().getIdWithPADZero(), supplier);
		if (StringUtils.isNotEmpty(previewImg02.getId())) {
			currentItem.setPreviewImage2(previewImg02);
		}
		
		Image previewImg03 = upgradeEmailForPreviewImageById(newItem.getPreviewImage3().getIdWithPADZero(), supplier);
		if (StringUtils.isNotEmpty(previewImg03.getId())) {
			currentItem.setPreviewImage3(previewImg03);
		}
		currentItem.setPrice(newItem.getSalePrice());
		currentItem.setDiscount(newItem.getDiscount());
		
		// Neu ti le chiet khau (C) > 0 thi can phai tinh toan gia ban: 
		// gia ban thuc te (A) = gia ban (B) - chiet khau (C)
		// Sau do cap nhat gia truoc khi chiet khau (B) cho gia cu, gia moi se la
		// gia ban thuc te (A).
		if (newItem.getDiscount() > 0) {
			currentItem.setSalePrice(newItem.getSalePrice() - newItem.getDiscount());
		} else {
			currentItem.setSalePrice(newItem.getSalePrice());
		}
		currentItem.setTotalWeight(newItem.getTotalWeight());
		currentItem.setTotalRemainingWeightAfterSell(newItem.getTotalWeight());
		currentItem.setDescription(newItem.getDescription());
		currentItem.setUpdatedAt(DateUtil.now());
		currentItem.setUpdatedUserIdAsDefault();
		return currentItem;
	}

}
