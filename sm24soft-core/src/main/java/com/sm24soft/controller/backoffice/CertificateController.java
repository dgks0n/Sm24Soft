package com.sm24soft.controller.backoffice;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.entity.Certificate;
import com.sm24soft.entity.Supplier;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.ICertificateService;
import com.sm24soft.service.ISupplierService;

@org.springframework.stereotype.Controller
@RequestMapping("/admin/certificate")
public class CertificateController extends ApplicationController {

	private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);
	
	private ISupplierService supplierService;
	
	private ICertificateService certificateService;
	
	@Autowired
	public CertificateController(ISupplierService supplierService, ICertificateService certificateService) {
		this.supplierService = supplierService;
		this.certificateService = certificateService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String renderCertificateResultPage(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
			final Model model) {
		logger.info("Call renderCertificateResultPage()");
		
		List<Certificate> listOfCertificates = null;
		
		try {
			if (StringUtils.isEmpty(keyword)) {
				listOfCertificates = certificateService.findAll();
			} else {
				listOfCertificates = certificateService.findAllBySupplierName(keyword);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("listOfCertificates", listOfCertificates);
		
		return "back-office/certificate/list";
	}
	
	@RequestMapping(path = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewCertificatePage(final Model model) {
		logger.info("Call renderCreateNewCertificatePage()");
		
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
	public @ResponseBody HttpResponse<String> createNewCertificate(@RequestBody Certificate certificate) {
		logger.info("Call createNewCertificate()");
		
		try {
			certificateService.createNewCertificate(certificate);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}

	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateCertificatePage(@PathVariable("id") String id, final Model model) {
		logger.info("Call renderUpdateCertificatePage()");
		
		try {
			List<Supplier> listOfSuppliers = supplierService.findAllSuppliers();
			model.addAttribute("listOfSuppliers", listOfSuppliers);
			
			Certificate certificate = certificateService.findById(id);
			model.addAttribute("certificate", certificate);
		} catch (IllegalArgumentException | ObjectNotFoundException ex) {
			logger.error(ex.getMessage(), ex);
			
			return redirectToError(HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("listOfSuppliers", null);
			model.addAttribute("certificate", null);
		}
		return "back-office/certificate/update";
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteCertificate(@PathVariable("id") String id) {
		logger.info("Call deleteCertificate()");
		
		try {
			certificateService.deleteById(id);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.PUT)
	public @ResponseBody HttpResponse<String> updateCertificate(
			@PathVariable("id") String id, @RequestBody Certificate certificate) {
		logger.info("Call updateCertificate()");
		
		try {
			certificate.setId(id);
			certificateService.updateCertificate(certificate);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
		return getOKStatus();
	}
	
}
