package com.sm24soft.repository;

import com.sm24soft.entity.RepresentativeOrContactPerson;

public interface RepresentativeOrContactPersonRepository extends Repository<RepresentativeOrContactPerson, String> {
	
	RepresentativeOrContactPerson findById(String id);
	
}
