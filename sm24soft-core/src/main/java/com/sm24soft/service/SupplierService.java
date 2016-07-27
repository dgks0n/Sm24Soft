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
import com.sm24soft.entity.Image;
import com.sm24soft.entity.RepresentativeOrContactPerson;
import com.sm24soft.entity.Supplier;
import com.sm24soft.repository.ImageRepository;
import com.sm24soft.repository.RepresentativeOrContactPersonRepository;
import com.sm24soft.repository.SupplierRepository;

@Service(ISupplierService.SERVICE_ID)
public class SupplierService implements ISupplierService {

	private SupplierRepository supplierRepository;

	private RepresentativeOrContactPersonRepository representativeOrContactPersonRepository;

	private ImageRepository imageRepository;

	@Autowired
	public SupplierService(SupplierRepository supplierRepository, ImageRepository imageRepository,
			RepresentativeOrContactPersonRepository representativeOrContactPersonRepository) {
		this.supplierRepository = supplierRepository;
		this.representativeOrContactPersonRepository = representativeOrContactPersonRepository;
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
		RepresentativeOrContactPerson representativePerson = supplier.getRepresentativePerson();

		// Representative person's short name checking
		if (!StringUtils.isEmpty(representativePerson.getShortName())) {
			representativePerson.setCreatedUserIdAsDefault();
			representativePerson.setUpdatedUserIdAsDefault();

			representativeOrContactPersonRepository.save(representativePerson);

			// If successfully created new one representative person for
			// supplier
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
	public String uploadRepresentativeLogo(String emailAddress, String logoFieldId, String logoUrl) {
		if (StringUtils.isEmpty(emailAddress)) {
			throw new IllegalArgumentException("The supplier's email address must not be null and empty");
		}
		
		if (StringUtils.isEmpty(logoUrl)) {
			throw new IllegalArgumentException("The image object must not be null and empty");
		}
	
		File logoFilePath = new File(logoUrl);
		if (!logoFilePath.exists()) {
			throw new IllegalArgumentException("Not found the image file.");
		}

		Supplier supplier = new Supplier();
		supplier.setEmail(emailAddress);
		
		Image logo = imageRepository.findLogoBySupplierEmailAddress(emailAddress, logoFieldId);
//		if (logo != null) {
//			logo.setImageUrl(logoUrl);
//			if (logo.getSupplier() == null) {
//				logo.setSupplier(supplier);
//			}
//			logo.setUpdatedAt(DateUtil.now());
//			imageRepository.update(logo);
//		} else {
//			logo = new Image();
//			logo.setImageFieldId(logoFieldId);
//			logo.setImageUrl(logoUrl);
//			logo.setKindOfImage(Image.LOGO_TYPE);
//			logo.setSupplier(supplier);
//			logo.setCreatedUserIdAsDefault();
//			logo.setUpdatedUserIdAsDefault();
//			imageRepository.save(logo);
//		}
		
		return logo.getIdWithPADZero();
	}

	@Override
	@Transactional(rollbackFor = {
			Exception.class }, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public String uploadOperationImage(String emailAddress, String imageFieldId, String imageUrl) {
		if (StringUtils.isEmpty(emailAddress)) {
			throw new IllegalArgumentException("The supplier's email address must not be null and empty");
		}
		
		if (StringUtils.isEmpty(imageUrl)) {
			throw new IllegalArgumentException("The image object must not be null and empty");
		}
	
		File imageFilePath = new File(imageUrl);
		if (!imageFilePath.exists()) {
			throw new IllegalArgumentException("Not found the image file.");
		}

		Supplier supplier = new Supplier();
		supplier.setEmail(emailAddress);
		
		Image image = imageRepository.findOperationImageBySupplierEmailAddressAndFieldId(emailAddress, 
				imageFieldId);
//		if (image != null) {
//			image.setImageUrl(imageUrl);
//			if (image.getSupplier() == null) {
//				image.setSupplier(supplier);
//			}
//			image.setUpdatedAt(DateUtil.now());
//			imageRepository.update(image);
//		} else {
//			image = new Image();
//			image.setImageFieldId(imageFieldId);
//			image.setImageUrl(imageUrl);
//			image.setKindOfImage(Image.LOGO_TYPE);
//			image.setSupplier(supplier);
//			image.setCreatedUserIdAsDefault();
//			image.setUpdatedUserIdAsDefault();
//			imageRepository.save(image);
//		}
		return image.getIdWithPADZero();
	}

	private RepresentativeOrContactPerson createNewOrUpdateRepresentOrContactPerson(
			RepresentativeOrContactPerson newROCPerson) {
		if (newROCPerson != null 
				&& StringUtils.isNotEmpty(newROCPerson.getShortName())
				&& StringUtils.isNotEmpty(newROCPerson.getEmail())) {
			
			RepresentativeOrContactPerson oldROCPerson = 
					representativeOrContactPersonRepository.findById(newROCPerson.getId());
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
				representativeOrContactPersonRepository.save(oldROCPerson);
			} else {
				oldROCPerson.setShortName(newROCPerson.getShortName());
				oldROCPerson.setFullName(newROCPerson.getFullName());
				oldROCPerson.setEmail(newROCPerson.getEmail());
				oldROCPerson.setPhoneNumber1(newROCPerson.getPhoneNumber1());
				oldROCPerson.setAddress1(newROCPerson.getAddress1());
				oldROCPerson.setUpdatedAt(DateUtil.now());
				oldROCPerson.setUpdatedUserIdAsDefault();
				
				// update existing object
				representativeOrContactPersonRepository.update(oldROCPerson);
			}
			return oldROCPerson;
		}
		
		// It's necessary for MyBatis mapping
		return new RepresentativeOrContactPerson();
	}
}
