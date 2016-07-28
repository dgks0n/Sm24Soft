package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.Certificate;

public interface CertificateRepository extends CrudRepository<Certificate, String> {

	List<Certificate> findAllBySupplierEmailAddress(String emailAddress);
	
	List<Certificate> findAllBySupplierName(String supplierName);
	
}
