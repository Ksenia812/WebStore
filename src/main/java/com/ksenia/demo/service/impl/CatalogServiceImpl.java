package com.ksenia.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ksenia.demo.model.Catalog;
import com.ksenia.demo.repository.CatalogRepository;
import com.ksenia.demo.service.ICatalogService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public class CatalogServiceImpl implements ICatalogService
{
	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public Catalog getCatalogById(Integer id)
	{
		return catalogRepository.getOne(id);
	}

	@Override
	public List<Catalog> getAllCatalogs()
	{
		return catalogRepository.findAll();
	}

	@Override
	public void addCatalog(Catalog catalog)
	{
		catalogRepository.saveAndFlush(catalog);
	}

	@Override
	public Catalog editCatalog(Catalog catalog)
	{
		return catalogRepository.saveAndFlush(catalog);
	}

	@Override
	public void deleteCatalog(Integer id)
	{
		catalogRepository.deleteById(id);
	}
}
