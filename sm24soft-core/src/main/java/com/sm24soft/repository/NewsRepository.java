package com.sm24soft.repository;

import java.util.List;

import com.sm24soft.entity.News;

public interface NewsRepository extends CrudRepository<News, String> {

	List<News> findAllBySupplierEmailAddress(String emailAddress);
}
