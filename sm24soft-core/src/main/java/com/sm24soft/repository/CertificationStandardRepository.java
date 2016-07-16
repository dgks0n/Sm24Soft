package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.CertificationStandard;

public interface CertificationStandardRepository extends CrudRepository<CertificationStandard, String> {

	List<CertificationStandard> findAllBySupplierEmailAddress(String emailAddress);
	
	List<CertificationStandard> findAllBySupplierName(String supplierName);
	
}
