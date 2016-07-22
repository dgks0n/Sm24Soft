package com.sm24soft.controller.backoffice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sm24soft.common.uuid.GenerateUUID;
import com.sm24soft.controller.ApplicationController;
import com.sm24soft.controller.Controllable;
import com.sm24soft.entity.AdvertiseImage;
import com.sm24soft.http.response.HttpResponse;
import com.sm24soft.service.IAdvertiseImageService;
import com.sm24soft.upload.bean.MultiPartFileUploadBean;

@Controller
@RequestMapping("/admin/advertise-image")
public class AdvertiseImageController extends ApplicationController implements Controllable {

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
	public @ResponseBody HttpResponse<String[]> uploadAdvertiseImage(@ModelAttribute("uploadForm") final MultiPartFileUploadBean multiPartFileUpload) {
		logger.info("Call uploadAdvertiseImage()");
		
		try {
			List<File> files = new ArrayList<>();
			for (MultipartFile multipartFile : multiPartFileUpload.getFiles()) {
				File targetFile = new File(com.sm24soft.util.FileUtil.getAdvertiseImagePath(File.separator 
						+ GenerateUUID.randomUUID())
						+ File.separator
						+ multipartFile.getOriginalFilename());
				multipartFile.transferTo(targetFile);
				files.add(targetFile);
			}
			String[] createdAdvertiseImages = advertiseImageService.createNewAdvertiseImagesByFiles(files);
			return getOKStatus(createdAdvertiseImages);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			
			return getErrorStatus(ex);
		}
	}

}
