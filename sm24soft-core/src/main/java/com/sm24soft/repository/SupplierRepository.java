package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, String> {

	List<Supplier> findTopSuppliersByLimitation(int limitation);

}
