package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Catalog;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface ICatalogService
{
	Catalog getCatalogById(Integer id);
	List<Catalog> getAllCatalogs();
	void addCatalog(Catalog catalog);
	Catalog editCatalog(Catalog catalog);
	void deleteCatalog(Integer id);
}
