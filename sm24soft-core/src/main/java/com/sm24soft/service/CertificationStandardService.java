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
import com.sm24soft.entity.CertificationStandard;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.CertificationStandardRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ICertificationStandardService.SERVICE_ID)
public class CertificationStandardService implements ICertificationStandardService {

	private SupplierRepository supplierRepository;
	
	private CertificationStandardRepository certificationStandardRepository;
	
	@Autowired
	public CertificationStandardService(SupplierRepository supplierRepository,
			CertificationStandardRepository certificationStandardRepository) {
		this.supplierRepository = supplierRepository;
		this.certificationStandardRepository = certificationStandardRepository;
	}

	@Override
	public List<CertificationStandard> findAllBySupplierName(String supplierName) {
		if (supplierName == null) {
			throw new IllegalArgumentException("Keyword must not be null");
		}
		
		String searchKeyword = getSearchKeywordPattern(supplierName);
		return certificationStandardRepository.findAllBySupplierName(searchKeyword);
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewCertificationStandard(CertificationStandard certificationStandard) {
		if (certificationStandard == null) {
			throw new IllegalArgumentException("The object must not be null");
		}
		
		if (StringUtils.isEmpty(certificationStandard.getName())) {
			throw new IllegalArgumentException("The name must not be null and empty");
		}
		
		Supplier belongToSupplier = certificationStandard.getSupplier();
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
		
		certificationStandard.setSupplier(belongToSupplier);
		certificationStandard.setCreatedAt(DateUtil.now());
		certificationStandard.setCreatedUserIdAsDefault();
		certificationStandard.setUpdatedAt(DateUtil.now());
		certificationStandard.setUpdatedUserIdAsDefault();
		
		certificationStandardRepository.save(certificationStandard);
		return certificationStandard.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateCertificationStandard(CertificationStandard certificationStandard) {
		if (certificationStandard == null || StringUtils.isEmpty(certificationStandard.getId())) {
			throw new IllegalArgumentException("The update object is invalid OR does not exist in database");
		}
		
		CertificationStandard oldCertificationStandard = certificationStandardRepository.findById(
				certificationStandard.getId());
		if (oldCertificationStandard == null) {
			throw new ObjectNotFoundException("The update object does not exist in database");
		}
		
		oldCertificationStandard.setName(certificationStandard.getName());
		oldCertificationStandard.setDescription(certificationStandard.getDescription());
		oldCertificationStandard.setDetailsLinkUrl(certificationStandard.getDetailsLinkUrl());
		oldCertificationStandard.setUpdatedAt(DateUtil.now());
		oldCertificationStandard.setUpdatedUserIdAsDefault();
		
		certificationStandardRepository.update(oldCertificationStandard);
		return oldCertificationStandard.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		certificationStandardRepository.deleteById(id);
	}
	
	/**
	 * Preparing search keyword pattern for the given keyword
	 * 
	 * @param keyword
	 * @return
	 */
	private String getSearchKeywordPattern(String keyword) {
		StringBuilder searchPatter = new StringBuilder("%");
		searchPatter.append(keyword);
		searchPatter.append("%");
		return searchPatter.toString();
	}

	@Override
	public CertificationStandard findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The Id must not be null and empty");
		}
		return certificationStandardRepository.findById(id);
	}
}
