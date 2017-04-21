package com.sm24soft.controller.backoffice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sm24soft.common.uuid.GenerateUUID;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.entity.AdvertiseImage;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IAdvertiseImageService;
import com.sm24soft.upload.bean.MultiPartFileUploadBean;
import com.sm24soft.util.FileUtil;

@Controller
@RequestMapping("/admin/advertise-image")
public class AdvertiseImageController extends ApplicationController {

	private static final Logger logger = LoggerFactory.getLogger(AdvertiseImageController.class);
	
	private IAdvertiseImageService advertiseImageService;
	
	@Autowired
	public AdvertiseImageController(IAdvertiseImageService advertiseImageService) {
		this.advertiseImageService = advertiseImageService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String renderListOfAdvertiseImagePage(final Model model) {
		logger.info("Call renderListOfAdvertiseImagePage()");
		
		List<AdvertiseImage> advertiseImages = new ArrayList<>();
		try {
			advertiseImages = advertiseImageService.findAll();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		
		model.addAttribute("advertiseImages", advertiseImages);
		return "back-office/advertise-image/list";
	}
	
	@RequestMapping(path = { "/create-new" }, method = RequestMethod.GET)
	public String renderCreateNewAdvertiseImagePage() {
		logger.info("Call renderCreateNewAdvertiseImagePage()");
		
		return "back-office/advertise-image/create";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String[]> uploadAdvertiseImage(
			@ModelAttribute("uploadForm") final MultiPartFileUploadBean fileUpload) {
		logger.info("Call uploadAdvertiseImage()");
		
		try {
			List<File> files = new ArrayList<>();
			for (MultipartFile file : fileUpload.getFiles()) {
				File targetDir = FileUtil.createResourceDirectory(getResourceDirectory(), GenerateUUID.randomUUID());
				File targetFile = new File(targetDir, file.getOriginalFilename());
				
				file.transferTo(targetFile);
				files.add(targetFile);
			}
			String[] createdAdvertiseImages = advertiseImageService.createNewAdvertiseImagesByFiles(files);
			return getOKStatus(createdAdvertiseImages);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.DELETE)
	public @ResponseBody HttpResponse<String> deleteAdvertiseImage(@PathVariable("id") final String id) {
		logger.info("Call deleteAdvertiseImage()");
		
		try {
			advertiseImageService.deleteById(id);
			return getOKStatus();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.GET)
	public String renderUpdateAdvertiseImagePage(@PathVariable("id") final String id, final Model model) {
		logger.info("Call renderUpdateAdvertiseImagePage()");
		
		try {
			AdvertiseImage advertiseImage = advertiseImageService.findById(id);
			
			model.addAttribute("advertiseImage", advertiseImage);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			model.addAttribute("advertiseImage", null);
		}
		return "back-office/advertise-image/update";
	}
	
	@RequestMapping(path = { "/{id}" }, method = RequestMethod.POST)
	public @ResponseBody HttpResponse<String> updateAdvertiseImage(@PathVariable("id") final String id, 
			@ModelAttribute("uploadForm") final MultiPartFileUploadBean fileUpload) {
		logger.info("Call updateAdvertiseImage()");
		
		try {
			if (ArrayUtils.isEmpty(fileUpload.getFiles())) {
				return getErrorStatus();
			}
			
			File targetDir = FileUtil.createResourceDirectory(getResourceDirectory(), GenerateUUID.randomUUID());
			MultipartFile sourceFile = fileUpload.getFiles()[0];
			File targetFile = new File(targetDir, sourceFile.getOriginalFilename());
			sourceFile.transferTo(targetFile);
			
			String createdAdvertiseImage = advertiseImageService.updateAdvertiseImage(id, targetFile);
			return getOKStatus(createdAdvertiseImage);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
	}
}
