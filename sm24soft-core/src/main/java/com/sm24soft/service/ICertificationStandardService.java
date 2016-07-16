package com.sm24soft.service;

import java.util.List;

import com.sm24soft.entity.CertificationStandard;

public interface ICertificationStandardService extends IService {
	
	static final String SERVICE_ID = "certificationStandardService";

	String createNewCertificationStandard(CertificationStandard certification);
	
	String updateCertificationStandard(CertificationStandard certification);
	
	List<CertificationStandard> findAllBySupplierName(String supplierName);
	
	void deleteById(String id);
	
	CertificationStandard findById(String id);
	
}
