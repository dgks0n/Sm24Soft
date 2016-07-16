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
import com.sm24soft.entity.ItemCategory;
import com.sm24soft.repository.ItemCategoryRepository;

@Service(IItemCategoryService.SERVICE_ID)
public class ItemCategoryService implements IItemCategoryService {
	
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
		this.itemCategoryRepository = itemCategoryRepository;
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNew(ItemCategory itemCategory) {
		if (itemCategory == null || StringUtils.isEmpty(itemCategory.getName())) {
			throw new IllegalArgumentException("Item category and its name must not be null and empty");
		}
		
		if (itemCategory.getMenuItem() == null || 
				StringUtils.isEmpty(itemCategory.getMenuItem().getId())) {
			throw new IllegalArgumentException("The item category must belong to one menu item");
		}
		
		if (itemCategory.getSupplier() == null ||
				StringUtils.isEmpty(itemCategory.getSupplier().getId())) {
			throw new IllegalArgumentException("The item category must belong to one supplier");
		}
		
		itemCategory.setCreatedAt(DateUtil.now());
		itemCategory.setCreatedUserIdAsDefault();
		itemCategory.setUpdatedAt(DateUtil.now());
		itemCategory.setUpdatedUserIdAsDefault();
		
		itemCategoryRepository.save(itemCategory);
		return itemCategory.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateById(ItemCategory itemCategory) {
		if (itemCategory == null ||
				StringUtils.isEmpty(itemCategory.getId())) {
			throw new IllegalArgumentException("The item category object or its id is invalid.");
		}
		
		ItemCategory oldItemCategory = itemCategoryRepository.findById(itemCategory.getId());
		if (oldItemCategory == null) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		oldItemCategory.setName(itemCategory.getName());
		oldItemCategory.setDescription(itemCategory.getDescription());
		oldItemCategory.setUpdatedAt(DateUtil.now());
		oldItemCategory.setUpdatedUserIdAsDefault();
		
		itemCategoryRepository.update(oldItemCategory);
		return oldItemCategory.getIdWithPADZero();
	}

	@Override
	public ItemCategory findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The id must not be null and empty");
		}
		return itemCategoryRepository.findById(id);
	}

	@Override
	public List<ItemCategory> findAllBySupplierId(String supplierId) {
		if (StringUtils.isEmpty(supplierId)) {
			throw new IllegalArgumentException("The sullier id must not be null and empty");
		}
		return itemCategoryRepository.findAllBySupplierId(supplierId);
	}

	@Override
	public List<ItemCategory> findAllByMenuItemId(String menuItemId) {
		if (StringUtils.isEmpty(menuItemId)) {
			throw new IllegalArgumentException("The menu item id must not be null and empty");
		}
		return itemCategoryRepository.findAllByMenuItemId(menuItemId);
	}

	@Override
	public List<ItemCategory> findAll() {
		return itemCategoryRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The id must not be null and empty");
		}
		itemCategoryRepository.deleteById(id);
	}

	@Override
	public List<ItemCategory> findAllBySupplierName(String supplierName) {
		String searchPattern = StringFormatUtil.appendPercentToLeftAndRight(supplierName);
		return itemCategoryRepository.findAllBySupplierName(searchPattern);
	}

}
