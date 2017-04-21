package com.sm24soft.service;

import java.util.List;

import com.sm24soft.entity.Certificate;

public interface ICertificateService extends IService {
	
	static final String SERVICE_ID = "certificateService";

	String createNewCertificate(Certificate certification);
	
	String updateCertificate(Certificate certification);
	
	List<Certificate> findAll();
	
	List<Certificate> findAllBySupplierName(String supplierName);
	
	void deleteById(String id);
	
	Certificate findById(String id);
	
}
