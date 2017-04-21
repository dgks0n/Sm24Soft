package com.sm24soft.service;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sm24soft.common.exception.ObjectNotFoundException;
import com.sm24soft.common.util.DateUtil;
import com.sm24soft.entity.BaseEntity.EntityStatus;
import com.sm24soft.entity.Image;
import com.sm24soft.entity.Image.ImageType;
import com.sm24soft.entity.RepresentativeOrContactPerson;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.ImageRepository;
import com.sm24soft.repository.RepresentativeOrContactPersonRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ISupplierService.SERVICE_ID)
public class SupplierService implements ISupplierService {

	private SupplierRepository supplierRepository;

	private RepresentativeOrContactPersonRepository rOCPersonRepository;

	private ImageRepository imageRepository;

	@Autowired
	public SupplierService(SupplierRepository supplierRepository, ImageRepository imageRepository,
			RepresentativeOrContactPersonRepository rOCPersonRepository) {
		this.supplierRepository = supplierRepository;
		this.rOCPersonRepository = rOCPersonRepository;
		this.imageRepository = imageRepository;
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String createNewSupplier(Supplier supplier) {
		if (supplier == null) {
			throw new IllegalArgumentException("The supplier object must not be null");
		}

		// Step 1: create new one representative person object for this supplier
		RepresentativeOrContactPerson representPerson = supplier.getRepresentativePerson();

		// Representative person's short name checking
		if (!StringUtils.isEmpty(representPerson.getShortName())) {
			representPerson.setCreatedUserIdAsDefault();
			representPerson.setUpdatedUserIdAsDefault();

			rOCPersonRepository.save(representPerson);

			// If successfully created new one representative person for
			// supplier
			supplier.setRepresentativePerson(representPerson);
		}

		// Step 2: create new one contact person object for this supplier
		RepresentativeOrContactPerson contactPerson = supplier.getContactPerson();
		if (!StringUtils.isEmpty(contactPerson.getShortName())) {
			contactPerson.setCreatedUserIdAsDefault();
			contactPerson.setUpdatedUserIdAsDefault();

			rOCPersonRepository.save(contactPerson);

			// If successfully created new one contact for this supplier
			supplier.setContactPerson(contactPerson);
		}
		
		// Step 3: active logo
		Image logo = supplier.getLogoUrl();
		if (logo == null || StringUtils.isEmpty(logo.getId())) {
			throw new IllegalArgumentException("Logo is required field");
		}
		
		logo = imageRepository.findById(logo.getId());
		if (logo == null) {
			throw new ObjectNotFoundException("Not found");
		}
		
		logo.setDeleteFlg(EntityStatus.ACTIVE.value());
		logo.setUpdatedAt(DateUtil.now());
		logo.setUpdatedUserIdAsDefault();
		
		imageRepository.update(logo);
		
		// Step 4: operation images
		for (Image image : supplier.getListOfImages()) {
			if (StringUtils.isNotEmpty(image.getId())) {
				image = imageRepository.findById(image.getId());
				if (image == null) {
					continue;
				}
				
				image.setDeleteFlg(EntityStatus.ACTIVE.value());
				image.setUpdatedAt(DateUtil.now());
				image.setUpdatedUserIdAsDefault();
				imageRepository.update(image);
			}
		}

		// Step 5: create new one supplier
		supplier.setCreatedUserIdAsDefault();
		supplier.setUpdatedUserIdAsDefault();

		supplierRepository.save(supplier);
		return supplier.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String updateSupplier(Supplier supplier) {
		/* 
		 * Supplier JSON:
		 * 
		 * {
		 * 	"companyName":"Trang trai Nuoi Trong Thuy San",
		 *  "companyTradingName":"Trang trai Nuoi Trong Thuy San",
		 *  "email":"nuoitrongthuysan@gmail.com",
		 *  "telephoneNumber1":"123456789",
		 *  "address1":"Ha Tinh",
		 *  "description":"Trang trại nuôi trồng thuỷ sản là một trong những trang trại tệ nhất từ trước đến nay\r\n",
		 *  "representativePerson":{
		 *  	"shortName":"",
		 *  	"fullName":"",
		 *  	"email":"",
		 *  	"phoneNumber1":"",
		 *  	"address1":""
		 *  },
		 *  "contactPerson":{
		 *  	"shortName":"",
		 *  	"fullName":"",
		 *  	"email":"",
		 *  	"phoneNumber1":"",
		 *  	"address1":""
		 *  }
		 * } 
		 */
		
		// get update object from database
		Supplier oldSupplier = findById(supplier.getId());
		if (oldSupplier == null || StringUtils.isEmpty(oldSupplier.getId())) {
			throw new ObjectNotFoundException("Object not found");
		}
		
		oldSupplier.setCompanyName(supplier.getCompanyName());
		oldSupplier.setCompanyTradingName(supplier.getCompanyTradingName());
		oldSupplier.setEmail(supplier.getEmail());
		oldSupplier.setTelephoneNumber1(supplier.getTelephoneNumber1());
		oldSupplier.setAddress1(supplier.getAddress1());
		oldSupplier.setDescription(supplier.getDescription());
		
		// representative person
		RepresentativeOrContactPerson oldRepresentPerson = oldSupplier.getRepresentativePerson();
		if (oldRepresentPerson != null) { 
			// if already existing in database then will update based on that object,
			// otherwise not exists then create new one
			supplier.getRepresentativePerson().setId(oldRepresentPerson.getId());
		}
		oldRepresentPerson = createNewOrUpdateRepresentOrContactPerson(supplier.getRepresentativePerson());
		oldSupplier.setRepresentativePerson(oldRepresentPerson);
		
		// contact person
		RepresentativeOrContactPerson oldContactPerson = oldSupplier.getContactPerson();
		if (oldContactPerson != null) {
			supplier.getContactPerson().setId(oldContactPerson.getId());
		}
		oldContactPerson = createNewOrUpdateRepresentOrContactPerson(supplier.getContactPerson());
		oldSupplier.setContactPerson(oldContactPerson);
		
		// logo & operation images
		Image logo = supplier.getLogoUrl();
		if (StringUtils.isNotEmpty(logo.getId())) {
			logo = imageRepository.findById(logo.getId());
			if (logo != null) {
				logo.setDeleteFlg(EntityStatus.ACTIVE.value());
				logo.setUpdatedAt(DateUtil.now());
				logo.setUpdatedUserIdAsDefault();
				
				imageRepository.update(logo);
			}
		}
		
		// Step 4: operation images
		for (Image image : supplier.getListOfImages()) {
			if (StringUtils.isNotEmpty(image.getId())) {
				image = imageRepository.findById(image.getId());
				if (image == null) {
					continue;
				}
				
				image.setDeleteFlg(EntityStatus.ACTIVE.value());
				image.setUpdatedAt(DateUtil.now());
				image.setUpdatedUserIdAsDefault();
				imageRepository.update(image);
			}
		}
		
		oldSupplier.setUpdatedAt(DateUtil.now());
		oldSupplier.setUpdatedUserIdAsDefault();
		
		supplierRepository.update(oldSupplier);
		return oldSupplier.getIdWithPADZero();
	}

	@Override
	public Supplier findById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		Supplier supplier = supplierRepository.findById(id);
		if (supplier == null) {
			throw new ObjectNotFoundException("Not found");
		}
		return supplier;
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
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new IllegalArgumentException("The object id must not be null and empty");
		}
		supplierRepository.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String uploadLogo(String email, String existingLogoId, ImageType imageType, File logoFile) {
		if (StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException("The supplier's email address must not be null and empty");
		}
	
		if (!logoFile.exists()) {
			throw new ObjectNotFoundException("File not found");
		}

		Supplier supplier = supplierRepository.findByEmailAddress(email);
		if (supplier == null) {
			supplier = new Supplier(email);
		}
		
		Image logo = null;
		
		if (StringUtils.isEmpty(existingLogoId)) {
			logo = new Image();
			logo.setAbsolutePath(logoFile.getPath());
			logo.setCaption(logoFile.getName());
			logo.setType(imageType.value());
			logo.setSupplierLoadedByEmail(supplier);
			logo.setDeleteFlg(EntityStatus.NON_ACTIVE.value());
			logo.setCreatedAt(DateUtil.now());
			logo.setCreatedUserIdAsDefault();
			logo.setUpdatedAt(DateUtil.now());
			logo.setUpdatedUserIdAsDefault();
			
			imageRepository.save(logo);
		} else {
			logo = imageRepository.findById(existingLogoId);
			if (logo == null) {
				throw new ObjectNotFoundException("Object not found");
			}
			
			logo.setAbsolutePath(logoFile.getPath());
			logo.setCaption(logoFile.getName());
			logo.setUpdatedAt(DateUtil.now());
			logo.setUpdatedUserIdAsDefault();
			
			imageRepository.update(logo);
		}
		
		return logo.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String uploadOperationImage(String email, String existingOperImageId, ImageType imageType, File imageFile) {
		if (StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException("The supplier's email address must not be null and empty");
		}

		if (!imageFile.exists()) {
			throw new IllegalArgumentException("File not found");
		}

		Supplier supplier = supplierRepository.findByEmailAddress(email);
		if (supplier == null) {
			supplier = new Supplier(email);
		}
		
		Image image = null;
		
		if (StringUtils.isEmpty(existingOperImageId)) {
			image = new Image();
			image.setAbsolutePath(imageFile.getPath());
			image.setCaption(imageFile.getName());
			image.setType(imageType.value());
			image.setSupplierLoadedByEmail(supplier);
			image.setDeleteFlg(EntityStatus.NON_ACTIVE.value());
			image.setCreatedAt(DateUtil.now());
			image.setCreatedUserIdAsDefault();
			image.setUpdatedAt(DateUtil.now());
			image.setUpdatedUserIdAsDefault();
			
			imageRepository.save(image);
		} else {
			image = imageRepository.findById(existingOperImageId);
			if (image == null) {
				throw new ObjectNotFoundException("Object not found");
			}
			
			image.setAbsolutePath(imageFile.getPath());
			image.setCaption(imageFile.getName());
			image.setUpdatedAt(DateUtil.now());
			image.setUpdatedUserIdAsDefault();
			
			imageRepository.update(image);
		}
		
		return image.getIdWithPADZero();
	}

	private RepresentativeOrContactPerson createNewOrUpdateRepresentOrContactPerson(
			RepresentativeOrContactPerson newROCPerson) {
		if (newROCPerson != null 
				&& StringUtils.isNotEmpty(newROCPerson.getShortName())
				&& StringUtils.isNotEmpty(newROCPerson.getEmail())) {
			
			RepresentativeOrContactPerson oldROCPerson = rOCPersonRepository.findById(newROCPerson.getId());
			if (oldROCPerson == null) {
				oldROCPerson = new RepresentativeOrContactPerson();
				oldROCPerson.setShortName(newROCPerson.getShortName());
				oldROCPerson.setFullName(newROCPerson.getFullName());
				oldROCPerson.setEmail(newROCPerson.getEmail());
				oldROCPerson.setPhoneNumber1(newROCPerson.getPhoneNumber1());
				oldROCPerson.setAddress1(newROCPerson.getAddress1());
				oldROCPerson.setCreatedAt(DateUtil.now());
				oldROCPerson.setCreatedUserIdAsDefault();
				oldROCPerson.setUpdatedAt(DateUtil.now());
				oldROCPerson.setUpdatedUserIdAsDefault();
				
				// create new one
				rOCPersonRepository.save(oldROCPerson);
			} else {
				oldROCPerson.setShortName(newROCPerson.getShortName());
				oldROCPerson.setFullName(newROCPerson.getFullName());
				oldROCPerson.setEmail(newROCPerson.getEmail());
				oldROCPerson.setPhoneNumber1(newROCPerson.getPhoneNumber1());
				oldROCPerson.setAddress1(newROCPerson.getAddress1());
				oldROCPerson.setUpdatedAt(DateUtil.now());
				oldROCPerson.setUpdatedUserIdAsDefault();
				
				// update existing object
				rOCPersonRepository.update(oldROCPerson);
			}
			return oldROCPerson;
		}
		
		// It's necessary for MyBatis mapping
		return new RepresentativeOrContactPerson();
	}
}
