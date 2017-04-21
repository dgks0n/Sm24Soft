package com.sm24soft.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.util.DateUtil;
import com.sm24soft.common.util.StringFormatUtil;
import com.sm24soft.entity.Certificate;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.CertificateRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ICertificateService.SERVICE_ID)
public class CertificateService implements ICertificateService {

	private SupplierRepository supplierRepository;
	
	private CertificateRepository certificateRepository;
	
	@Autowired
	public CertificateService(SupplierRepository supplierRepository, CertificateRepository certificateRepository) {
		this.supplierRepository = supplierRepository;
		this.certificateRepository = certificateRepository;
	}

	@Override
	public List<Certificate> findAllBySupplierName(String supplierName) {
		String searchKeyword = StringFormatUtil.appendPercentToLeftAndRight(supplierName);
		return certificateRepository.findAllBySupplierName(searchKeyword);
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewCertificate(Certificate certificate) {
		if (certificate == null) {
			throw new IllegalArgumentException("The object must not be null");
		}
		
		if (StringUtils.isEmpty(certificate.getName())) {
			throw new IllegalArgumentException("The name must not be null and empty");
		}
		
		Supplier belongToSupplier = certificate.getSupplier();
		if (belongToSupplier == null || StringUtils.isEmpty(belongToSupplier.getId())) {
			throw new IllegalArgumentException("Cannot create any certificate object if current "
					+ "creating information does not valid.");
		}
		
		// Extract supplier's email for certification standard
		belongToSupplier = supplierRepository.findById(belongToSupplier.getId());
		if (belongToSupplier == null) {
			throw new ObjectNotFoundException("Belong to supplier object does not exist");
		}
		
		certificate.setSupplier(belongToSupplier);
		certificate.setCreatedAt(DateUtil.now());
		certificate.setCreatedUserIdAsDefault();
		certificate.setUpdatedAt(DateUtil.now());
		certificate.setUpdatedUserIdAsDefault();
		
		certificateRepository.save(certificate);
		return certificate.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateCertificate(Certificate newCertificate) {
		if (newCertificate == null || StringUtils.isEmpty(newCertificate.getId())) {
			throw new IllegalArgumentException("The update object is invalid OR does not exist in database");
		}
		
		Certificate oldCertificate = certificateRepository.findById(newCertificate.getId());
		if (oldCertificate == null) {
			throw new ObjectNotFoundException("The update object does not exist in database");
		}
		
		oldCertificate.setName(newCertificate.getName());
		oldCertificate.setDescription(newCertificate.getDescription());
		oldCertificate.setDetailsLinkUrl(newCertificate.getDetailsLinkUrl());
		oldCertificate.setUpdatedAt(DateUtil.now());
		oldCertificate.setUpdatedUserIdAsDefault();
		
		certificateRepository.update(oldCertificate);
		return oldCertificate.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		certificateRepository.deleteById(id);
	}

	@Override
	public Certificate findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		Certificate certification = certificateRepository.findById(id);
		if (certification == null) {
			throw new ObjectNotFoundException("Not found");
		}
		return certification;
	}

	@Override
	public List<Certificate> findAll() {
		return certificateRepository.findAll();
	}
}
