package com.sm24soft.controller.backoffice;

import java.util.List;

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

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.CertificationStandard;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.ICertificationStandardService;
import com.sm24soft.service.ISupplierService;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/certification")
public class CertificationStandardController extends ApplicationController implements Controllable {

	private static final Logger logger = LoggerFactory.getLogger(CertificationStandardController.class);
	
	private ISupplierService supplierService;
	
	private ICertificationStandardService certificationStandardService;
	
	@Autowired
	public CertificationStandardController(ISupplierService supplierService,
			ICertificationStandardService certificationStandardService) {
		this.supplierService = supplierService;
		this.certificationStandardService = certificationStandardService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String renderCertificationStandardResultPage(
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
			final Model model) {
		logger.info("Call renderCertificationStandardResultPage()");
		
		List<CertificationStandard> listOfCertificationStandards = null;
		
		try {
			if (StringUtils.isEmpty(keyword)) {
				listOfCertificationStandards = certificationStandardService.findAll();
			} else {
				listOfCertificationStandards = certificationStandardService.findAllBySupplierName(keyword);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listOfCertificationStandards", listOfCertificationStandards);
		
		return "back-office/certificate/list";
	}
	
	@RequestMapping(path = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewCertificationStandardPage(final Model model) {
		logger.info("Call renderCreateNewCertificationStandardPage()");
		
		try {
			List<Supplier> listOfSuppliers = supplierService.findAllSuppliers();
			model.addAttribute("listOfSuppliers", listOfSuppliers);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("listOfSuppliers", null);
		}
		return "back-office/certificate/create-new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> createNewCertificationStandard(
			@RequestBody CertificationStandard certificationStandard) {
		logger.info("Call createNewCertificationStandard()");
		
		try {
			certificationStandardService.createNewCertificationStandard(certificationStandard);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}

	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateCertificationStandardPage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderUpdateCertificationStandardPage()");
		
		try {
			List<Supplier> listOfSuppliers = supplierService.findAllSuppliers();
			model.addAttribute("listOfSuppliers", listOfSuppliers);
			
			CertificationStandard certificationStandard = certificationStandardService.findById(id);
			model.addAttribute("certificationStandard", certificationStandard);
		} catch (IllegalArgumentException | ObjectNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			
			return getRedirectTo404Page();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("listOfSuppliers", null);
			model.addAttribute("certificationStandard", null);
		}
		return "back-office/certificate/update";
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteCertificationStandard(@PathVariable("id") String id) {
		logger.info("Call deleteCertificationStandard()");
		
		try {
			certificationStandardService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateCertificationStandard(
			@PathVariable("id") String id,
			@RequestBody CertificationStandard certification) {
		logger.info("Call updateCertificationStandard()");
		
		try {
			certification.setId(id);
			certificationStandardService.updateCertificationStandard(certification);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
}
