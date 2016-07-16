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
import com.sm24soft.entity.CertificationStandard;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.CertificationStandardRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ICertificationStandardService.SERVICE_ID)
public class CertificationStandardService implements ICertificationStandardService {

	private SupplierRepository supplierRepository;
	
	private CertificationStandardRepository certificationRepository;
	
	@Autowired
	public CertificationStandardService(SupplierRepository supplierRepository,
			CertificationStandardRepository certificationRepository) {
		this.supplierRepository = supplierRepository;
		this.certificationRepository = certificationRepository;
	}

	@Override
	public List<CertificationStandard> findAllBySupplierName(String supplierName) {
		String searchKeyword = StringFormatUtil.appendPercentToLeftAndRight(supplierName);
		return certificationRepository.findAllBySupplierName(searchKeyword);
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewCertificationStandard(CertificationStandard certification) {
		if (certification == null) {
			throw new IllegalArgumentException("The object must not be null");
		}
		
		if (StringUtils.isEmpty(certification.getName())) {
			throw new IllegalArgumentException("The name must not be null and empty");
		}
		
		Supplier belongToSupplier = certification.getSupplier();
		if (belongToSupplier == null || StringUtils.isEmpty(belongToSupplier.getId())) {
			throw new IllegalArgumentException(
					"Cannot create any certification standard object if current "
					+ "creating information does not valid.");
		}
		
		// Extract supplier's email for certification standard
		belongToSupplier = supplierRepository.findById(belongToSupplier.getId());
		if (belongToSupplier == null) {
			throw new ObjectNotFoundException("Belong to supplier object does not exist");
		}
		
		certification.setSupplier(belongToSupplier);
		certification.setCreatedAt(DateUtil.now());
		certification.setCreatedUserIdAsDefault();
		certification.setUpdatedAt(DateUtil.now());
		certification.setUpdatedUserIdAsDefault();
		
		certificationRepository.save(certification);
		return certification.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateCertificationStandard(CertificationStandard certification) {
		if (certification == null || StringUtils.isEmpty(certification.getId())) {
			throw new IllegalArgumentException("The update object is invalid OR does not exist in database");
		}
		
		CertificationStandard oldCertificationStandard = certificationRepository.findById(
				certification.getId());
		if (oldCertificationStandard == null) {
			throw new ObjectNotFoundException("The update object does not exist in database");
		}
		
		oldCertificationStandard.setName(certification.getName());
		oldCertificationStandard.setDescription(certification.getDescription());
		oldCertificationStandard.setDetailsLinkUrl(certification.getDetailsLinkUrl());
		oldCertificationStandard.setUpdatedAt(DateUtil.now());
		oldCertificationStandard.setUpdatedUserIdAsDefault();
		
		certificationRepository.update(oldCertificationStandard);
		return oldCertificationStandard.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		certificationRepository.deleteById(id);
	}

	@Override
	public CertificationStandard findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		return certificationRepository.findById(id);
	}

	@Override
	public List<CertificationStandard> findAll() {
		return certificationRepository.findAll();
	}
}
