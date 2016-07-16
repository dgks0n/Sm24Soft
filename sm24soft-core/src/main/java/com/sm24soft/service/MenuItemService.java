package com.sm24soft.service;

import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.common.exception.DuplicateKeyException;
import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.util.DateUtil;
import com.sm24soft.entity.MenuItem;
import com.sm24soft.repository.MenuItemRepository;

@Service(IMenuItemService.SERVICE_ID)
public class MenuItemService implements IMenuItemService {

	private MenuItemRepository menuItemRepository;
	
	@Autowired
	public MenuItemService(MenuItemRepository menuItemRepository) {
		this.menuItemRepository = menuItemRepository;
	}

	@Override
	public List<MenuItem> findAllMenuItems() {
		return menuItemRepository.findAllMenuItems();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, 
		propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String creatNewMenuItem(MenuItem menuItem) {
		if (null == menuItem) {
			throw new NullArgumentException("The object is null");
		}
		
		if (StringUtils.isEmpty(menuItem.getFullNameOfMenuItem())) {
			throw new IllegalArgumentException("The fullname of item must not be null and empty");
		}
		
		// Checking existing or not
		MenuItem tempMenuItem = menuItemRepository.findByFullName(menuItem.getFullNameOfMenuItem());
		if (tempMenuItem != null) {
			throw new DuplicateKeyException("The name of menu item already existing in the database");
		}
		
		// if does not exist in the database
		menuItem.setCreatedUserIdAsDefault();
		menuItem.setUpdatedUserIdAsDefault();
		menuItemRepository.save(menuItem);
		
		// return created menu item id
		return menuItem.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, 
		propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateMenuItem(MenuItem menuItem) {
		if (null == menuItem) {
			throw new NullArgumentException("The object is null");
		}
		
		if (StringUtils.isEmpty(menuItem.getFullNameOfMenuItem())) {
			throw new IllegalArgumentException("The fullname of item must not be null and empty");
		}
		
		MenuItem oldMenuItem = menuItemRepository.findById(menuItem.getId());
		if (oldMenuItem == null || StringUtils.isEmpty(menuItem.getId())) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		menuItem.setUpdatedAt(DateUtil.now());
		menuItem.setUpdatedUserIdAsDefault();
		menuItemRepository.update(menuItem);
		return menuItem.getIdWithPADZero();
	}

	@Override
	public MenuItem findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		return menuItemRepository.findById(id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, 
		propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		menuItemRepository.deleteById(id);
	}

}
