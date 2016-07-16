package com.sm24soft.controller.backoffice;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.ItemCategory;
import com.sm24soft.entity.MenuItem;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IItemCategoryService;
import com.sm24soft.service.IMenuItemService;
import com.sm24soft.service.ISupplierService;

@Controller
@RequestMapping("/admin/item-category")
public class ItemCategoryController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(ItemCategoryController.class);
	
	private IItemCategoryService itemCategoryService;
	
	private IMenuItemService menuItemService;
	
	private ISupplierService supplierService;
	
	@Autowired
	public ItemCategoryController(IItemCategoryService itemCategoryService,
			IMenuItemService menuItemService, ISupplierService supplierService) {
		this.itemCategoryService = itemCategoryService;
		this.menuItemService = menuItemService;
		this.supplierService = supplierService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String renderItemCategoryResultPage(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			final Model model) {
		logger.info("Call renderItemCategoryResultPage()");
		
		List<ItemCategory> listOfItemCategories = null;
		
		try {
			if (StringUtils.isEmpty(keyword)) {
				listOfItemCategories = itemCategoryService.findAll();
			} else {
				listOfItemCategories = itemCategoryService.findAllBySupplierName(keyword);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listOfItemCategories", listOfItemCategories);
		return "back-office/item-category/list";
	}
	
	@RequestMapping(value = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewItemCategoryPage(final Model model) {
		logger.info("Call renderCreateNewItemCategoryPage()");
		
		List<MenuItem> listOfMenuItems = null;
		List<Supplier> listOfSuppliers = null;
		
		try {
			listOfMenuItems = menuItemService.findAllMenuItems();
			listOfSuppliers = supplierService.findAllSuppliers();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("listOfMenuItems", listOfMenuItems);
		model.addAttribute("listOfSuppliers", listOfSuppliers);
		return "back-office/item-category/create-new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewItemCategory(@RequestBody ItemCategory itemCategory) {
		logger.info("Call createNewItemCategory()");
		
		if (itemCategory == null) {
			return getErrorStatus();
		}
		
		try {
			itemCategoryService.createNew(itemCategory);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteItemCategory(@PathVariable("id") String id) {
		logger.info("Call deleteItemCategory()");
		
		try {
			itemCategoryService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateItemCategory(@PathVariable("id") String id, 
			@RequestBody ItemCategory itemCategory) {
		logger.info("Call updateItemCategory()");
		
		try {
			itemCategory.setId(id);
			itemCategoryService.updateById(itemCategory);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateItemCategoryPage(@PathVariable("id") String id,
			final Model model) {
		try {
			ItemCategory itemCategory = itemCategoryService.findById(id);
			model.addAttribute("itemCategory", itemCategory);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("itemCategory", null);
		}
		
		List<MenuItem> listOfMenuItems = null;
		List<Supplier> listOfSuppliers = null;
		
		try {
			listOfMenuItems = menuItemService.findAllMenuItems();
			listOfSuppliers = supplierService.findAllSuppliers();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("listOfMenuItems", listOfMenuItems);
		model.addAttribute("listOfSuppliers", listOfSuppliers);
		
		return "back-office/item-category/update";
	}
}
