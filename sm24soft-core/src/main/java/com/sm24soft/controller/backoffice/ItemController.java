package com.sm24soft.controller.backoffice;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.uuid.GenerateUUID;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.Item;
import com.sm24soft.entity.ItemCategory;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IItemCategoryService;
import com.sm24soft.service.IItemService;
import com.sm24soft.service.ISupplierService;

@Controller
@RequestMapping("/admin/item")
public class ItemController extends ApplicationController implements Controllable {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	private IItemService itemService;
	
	private ISupplierService supplierService;
	
	private IItemCategoryService itemCategoryService;
	
	@Autowired
	public ItemController(IItemService itemService, ISupplierService supplierService,
			IItemCategoryService itemCategoryService) {
		this.itemService = itemService;
		this.supplierService = supplierService;
		this.itemCategoryService = itemCategoryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String renderItemResultPage(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			final Model model) {
		logger.info("Call renderItemResultPage()");
		
		List<Item> listOfItems = null;
		
		try {
			if (StringUtils.isEmpty(keyword)) {
				listOfItems = itemService.findAll();
			} else {
				listOfItems = itemService.findAllBySupplierName(keyword);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listOfItems", listOfItems);
		
		return "back-office/item/list";
	}
	
	@RequestMapping(value = "/create-new", method = RequestMethod.GET)
	public String renderCreateNewItemPage(final Model model) {
		logger.info("Call renderCreateNewItemPage()");
		
		List<Supplier> listOfSuppliers = null;
		
		try {
			// load all of suppliers for default
			listOfSuppliers = supplierService.findAllSuppliers();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	
		model.addAttribute("listOfSuppliers", listOfSuppliers);
		return "back-office/item/create-new";
	}
	
	@RequestMapping(value = "/upload-preview-image", method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> uploadPreviewImage(
			@RequestParam(value = "file", required = true) final MultipartFile multipartFile,
			@RequestParam(value = "imageId", required = false, defaultValue = "") final String imageId,
			@RequestParam(value = "imageFieldId", required = true) final String imageField) {
		logger.info("Call uploadPreviewImage()");
		
		try {
			File targetFile = null;
			
			// In-case create new one
			if (StringUtils.isEmpty(imageId)) {
				targetFile = new File(com.sm24soft.util.FileUtil.getItemImagePath(imageField)
						+ File.separator
						+ GenerateUUID.randomUUID());
			} else { // In-case update
				targetFile = new File(com.sm24soft.util.FileUtil.getItemImagePath(imageField)
						+ File.separator
						+ imageId);
			}
			multipartFile.transferTo(targetFile);
			
			String actualImageId = itemService.uploadImageForItem(imageId, imageField, targetFile.getPath());
			return getOKStatus(actualImageId);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewItem(@RequestBody Item item) {
		logger.info("Call createNewItem()");
		
		try {
			itemService.createNewItem(item);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String renderUpdateItemPage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderUpdateItemPage()");
		
		return renderItemInfoPage(id, model, "back-office/item/update");
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateItem(@PathVariable("id") String id,
			@RequestBody Item item) {
		logger.info("Call updateItem()");
		
		try {
			item.setId(id);
			itemService.updateItem(item);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = "/copy-item/{id}", method = RequestMethod.GET)
	public String renderCopyItemPage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderCopyItemPage()");
		
		return renderItemInfoPage(id, model, "back-office/item/copy");
	}
	
	@RequestMapping(value = "/copy-item/{id}", method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> copyItemFromAnotherItem(@PathVariable("id") String id,
			@RequestBody Item item) {
		logger.info("Call copyItemFromAnotherItem()");
		
		try {
			item.setId(id);
			itemService.copyItemFromAnotherItem(id, item);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	private String renderItemInfoPage(final String itemId, final Model model, 
			final String viewPage) {
		List<Supplier> listOfSuppliers = null;
		List<ItemCategory> listOfItemCategories = null;
		
		try {
			Item item = itemService.findById(itemId);
			
			model.addAttribute("item", item);
			
			// load all of suppliers for default
			listOfSuppliers = supplierService.findAllSuppliers();
			listOfItemCategories = itemCategoryService.findAllBySupplierId(item.getSupplier().getIdWithPADZero());
		} catch (IllegalArgumentException | ObjectNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			
			return getRedirectTo404Page();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	
		model.addAttribute("listOfSuppliers", listOfSuppliers);
		model.addAttribute("listOfItemCategories", listOfItemCategories);
		return viewPage;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteItem(@PathVariable("id") String id) {
		try {
			itemService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
}
