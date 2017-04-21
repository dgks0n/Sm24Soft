package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.ItemDetail;

public interface ItemDetailRepository extends CrudRepository<ItemDetail, String> {

	List<ItemDetail> findAllByParentInvoiceId(String id);
}
