package com.sm24soft.controller.backoffice;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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

import com.sm24soft.common.util.FileUtil;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.ISupplierService;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/supplier")
public class SupplierController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	private ISupplierService supplierService;
	
	@Autowired
	public SupplierController(ISupplierService supplierService) {
		this.supplierService = supplierService;
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
			@RequestParam(value = "imageFieldId", required = true, defaultValue = "LOGO") String imageFieldId) {
		logger.info("Call createOrUpdateLogoUrl()");
		
		if (StringUtils.isEmpty(emailAddress)) {
			return getErrorStatus();
		}
		
		File targetFile = null;
		
		try {
			String supplierFolder = FileUtil.getHomeDirectory("Gala/Resources") + File.separator + emailAddress;
			File logoDir  = new File(supplierFolder + File.separator + "logo");
			if (!logoDir.exists()) {
				logoDir.mkdirs();
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			if (StringUtils.isNotEmpty(originalFilename)) {
				// Handle file content - multipartFile.getInputStream()
				targetFile = new File(logoDir.getPath() + File.separator + originalFilename);
				multipartFile.transferTo(targetFile);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
		
		if (targetFile != null) {
			try {
				supplierService.uploadRepresentativeLogo(emailAddress, imageFieldId, targetFile.getPath());
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				return getErrorStatus(ex);
			}
		}
		
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/upload-image" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> uploadOperationImages(
			@RequestParam(value = "email", required = true) String emailAddress,
			@RequestParam(value = "file", required = true) MultipartFile multipartFile, 
			@RequestParam(value = "imageFieldId", required = true, defaultValue = "LOGO") String imageFieldId) {
		if (StringUtils.isEmpty(emailAddress)) {
			return getErrorStatus();
		}
		
		File targetFile = null;
		
		try {
			String supplierFolder = FileUtil.getHomeDirectory() + File.separator + emailAddress;
			File imageDir  = new File(supplierFolder + File.separator + "image");
			if (!imageDir.exists()) {
				imageDir.mkdirs();
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			if (StringUtils.isNotEmpty(originalFilename)) {
				// Handle file content - multipartFile.getInputStream()
				targetFile = new File(imageDir.getPath() + File.separator + originalFilename);
				multipartFile.transferTo(targetFile);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
		
		if (targetFile != null) {
			try {
				supplierService.uploadOperationImage(emailAddress, imageFieldId, targetFile.getPath());
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				return getErrorStatus(ex);
			}
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteOneSupplier(@PathVariable("id") String id) {
		logger.info("Call deleteOneSupplier()");
		
		if (StringUtils.isEmpty(id)) {
			return getErrorStatus();
		}
		
		try {
			supplierService.deleteById(id);
			return getOKStatus();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateSupplierPage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderUpdateSupplierPage()");
		
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The supplier's id must not be null and empty");
		}
		
		Supplier supplier = null;
		
		try {
			supplier = supplierService.findById(id);
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
		
		if (StringUtils.isEmpty(id)) {
			return getErrorStatus();
		}
		
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
	
}
