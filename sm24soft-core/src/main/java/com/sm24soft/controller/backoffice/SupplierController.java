package com.sm24soft.controller.backoffice;

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
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping(path = { "" }, method = RequestMethod.GET)
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
		return "back-office/supplier/index";
	}
	
	@RequestMapping(path = { "/create-new-one" }, method = RequestMethod.GET)
	public String renderCreateNewOneSupplier() {
		logger.info("Call renderCreateNewOneSupplier()");
		
		return "back-office/supplier/create-new";
	}
	
	@RequestMapping(path = { "/create-new-one" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewSupplier(@RequestBody Supplier supplier) {
		logger.info("Call createNewSupplier()");
		
		try {
			supplierService.createNewSupplier(supplier);
			
			return getOKStatus();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
	}
	
	@RequestMapping(path = { "/delete/{id}" }, method = RequestMethod.POST)
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
}
