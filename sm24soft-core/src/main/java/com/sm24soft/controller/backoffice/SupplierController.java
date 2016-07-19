package com.sm24soft.controller.backoffice;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.ItemCategory;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IItemCategoryService;
import com.sm24soft.service.ISupplierService;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/supplier")
public class SupplierController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	private ISupplierService supplierService;
	
	private IItemCategoryService itemCategoryService;
	
	@Autowired
	public SupplierController(ISupplierService supplierService, IItemCategoryService itemCategoryService) {
		this.supplierService = supplierService;
		this.itemCategoryService = itemCategoryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String renderListOfSuppliersPage(final Model model) {
		logger.info("Call renderListOfSuppliersPage()");
		
		try {
			List<Supplier> suppliers = null;
			suppliers = supplierService.findAllSuppliers();
			
			// the list of suppliers existing in our system
			model.addAttribute("listOfSuppliers", suppliers);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("listOfSuppliers", CollectionUtils.EMPTY_COLLECTION);
		}
		return "back-office/supplier/list";
	}
	
	@RequestMapping(path = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewOneSupplier() {
		logger.info("Call renderCreateNewOneSupplier()");
		
		return "back-office/supplier/create-new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewSupplier(@RequestBody Supplier supplier) {
		logger.info("Call createNewSupplier()");
		
		try {
			supplierService.createNewSupplier(supplier);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/upload-logo" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createOrUpdateLogoUrl(
			@RequestParam(value = "email", required = true) String emailAddress,
			@RequestParam(value = "file", required = true) MultipartFile multipartFile, 
			@RequestParam(value = "imageFieldId", required = true, defaultValue = "LOGO") String imageField) {
		logger.info("Call createOrUpdateLogoUrl()");
		
		try {
			File targetFile = new File(com.sm24soft.util.FileUtil.getSupplierLogoPath(emailAddress) 
					+ File.separator 
					+ multipartFile.getOriginalFilename());
			multipartFile.transferTo(targetFile);
			
			supplierService.uploadRepresentativeLogo(emailAddress, imageField, targetFile.getPath());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/upload-image" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> uploadOperationImages(
			@RequestParam(value = "email", required = true) String emailAddress,
			@RequestParam(value = "file", required = true) MultipartFile multipartFile, 
			@RequestParam(value = "imageFieldId", required = true, defaultValue = "LOGO") String imageField) {
		logger.info("Call uploadOperationImages()");
		
		try {
			File targetFile = new File(com.sm24soft.util.FileUtil.getSupplierOperationImagePath(emailAddress, imageField)
					+ File.separator
					+ multipartFile.getOriginalFilename());
			multipartFile.transferTo(targetFile);
			
			supplierService.uploadOperationImage(emailAddress, imageField, targetFile.getPath());
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteOneSupplier(@PathVariable("id") String id) {
		logger.info("Call deleteOneSupplier()");
		
		try {
			supplierService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateSupplierPage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderUpdateSupplierPage()");
		
		Supplier supplier = null;
		
		try {
			supplier = supplierService.findById(id);
		} catch (IllegalArgumentException | ObjectNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			
			return getRedirectTo404Page();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("supplier", supplier);
		return "back-office/supplier/update";
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateSupplierPage(@PathVariable("id") String id, 
			@RequestBody Supplier supplier) {
		logger.info("Call updateSupplierPage()");
		
		try {
			supplier.setId(id);
			// push to underlying database
			supplierService.updateSupplier(supplier);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(value = { "/item-categories" }, method = RequestMethod.GET)
	public @ResponseBody HttpResponse<String> renderItemCategorySelectBox(
			@RequestParam(value = "supplierId", required = true) String supplierId) {
		logger.info("Call renderItemCategorySelectBox()");
		
		StringBuilder optionsBuilder = new StringBuilder("<option value=\"\"></option>");
		try {
			List<ItemCategory> listOfItemCategories = itemCategoryService.findAllBySupplierId(supplierId);
			for (ItemCategory itemCategory : listOfItemCategories) {
				optionsBuilder.append("<option value=\"");
				optionsBuilder.append(itemCategory.getIdWithPADZero());
				optionsBuilder.append("\">");
				optionsBuilder.append(itemCategory.getName());
				optionsBuilder.append("</option>");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return getOKStatus(optionsBuilder.toString());
	}
}
