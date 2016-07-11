package com.sm24soft.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.entity.RepresentativeOrContactPerson;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.RepresentativeOrContactPersonRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ISupplierService.SERVICE_ID)
public class SupplierService implements ISupplierService {
	
	private SupplierRepository supplierRepository;
	
	private RepresentativeOrContactPersonRepository representativeOrContactPersonRepository;

	@Autowired
	public SupplierService(SupplierRepository supplierRepository,
			RepresentativeOrContactPersonRepository representativeOrContactPersonRepository) {
		this.supplierRepository = supplierRepository;
		this.representativeOrContactPersonRepository = representativeOrContactPersonRepository;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, 
		propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewSupplier(Supplier supplier) {
		if (supplier == null) {
			throw new IllegalArgumentException("The supplier object must not be null");
		}
		
		// Step 1: create new one representative person object for this supplier
		RepresentativeOrContactPerson representativePerson = supplier.getRepresentativePerson();
		
		// Representative person's short name checking
		if (!StringUtils.isEmpty(representativePerson.getShortName())) {
			representativePerson.setCreatedUserIdAsDefault();
			representativePerson.setUpdatedUserIdAsDefault();
			
			representativeOrContactPersonRepository.save(representativePerson);
			
			// If successfully created new one representative person for supplier
			supplier.setRepresentativePerson(representativePerson);
		}
		
		// Step 2: create new one contact person object for this supplier
		RepresentativeOrContactPerson contactPerson = supplier.getContactPerson();
		if (!StringUtils.isEmpty(contactPerson.getShortName())) {
			contactPerson.setCreatedUserIdAsDefault();
			contactPerson.setUpdatedUserIdAsDefault();
			
			representativeOrContactPersonRepository.save(contactPerson);
			
			// If successfully created new one contact for this supplier
			supplier.setContactPerson(contactPerson);
		}
		
		// Step 3: create new one supplier
		supplier.setCreatedUserIdAsDefault();
		supplier.setUpdatedUserIdAsDefault();
		
		supplierRepository.save(supplier);
		return supplier.getIdWithPADZero();
	}

	@Override
	public String updateSupplier(Supplier supplier) {
		return null;
	}

	@Override
	public Supplier findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		
		return supplierRepository.findById(id);
	}

	@Override
	public List<Supplier> findAllSuppliers() {
		List<Supplier> suppliers = supplierRepository.findAll();
		if (CollectionUtils.isEmpty(suppliers)) {
			return null;
		}
		return suppliers;
	}

	@Override
	public List<Supplier> findTopSuppliersByLimitation(int limitation) {
		if (limitation < 0) {
			return findAllSuppliers();
		}
		return supplierRepository.findTopSuppliersByLimitation(limitation);
	}

	@Override
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		supplierRepository.deleteById(id);
	}

}
