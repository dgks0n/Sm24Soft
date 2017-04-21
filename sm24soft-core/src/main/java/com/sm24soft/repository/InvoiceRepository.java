package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {
	
	List<Invoice> findAllSubInvoicesByParentInvoiceId(String id);
	
	// A couple of status
	// -	1: WAITING_FOR_CONFIRM, 
	// -	2: CONFIRMED_AND_WAITING_FOR_SHIP, 
	// -	3: SHIPPED, 
	// -	4: CANCELED
	
	List<Invoice> findByActualStatus(String actualStatus);
	
	List<Invoice> findAllWaitingForConfirmInvoices();
	
	List<Invoice> findAllConfirmedAndWaitingForShipInvoices();
	
	List<Invoice> findAllShippedInvoices();
	
	List<Invoice> findAllCanceledInvoices();
}
