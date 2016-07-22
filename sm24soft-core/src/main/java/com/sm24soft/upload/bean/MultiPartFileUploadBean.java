package com.sm24soft.upload.bean;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileUploadBean {

	private String type;
	private MultipartFile[] files;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

}
