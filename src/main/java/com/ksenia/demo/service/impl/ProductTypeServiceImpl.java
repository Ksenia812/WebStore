package com.ksenia.demo.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.ProductType;
import com.ksenia.demo.repository.CategoryRepository;
import com.ksenia.demo.repository.ProductRepository;
import com.ksenia.demo.repository.ProductTypeRepository;
import com.ksenia.demo.repository.UserRepository;
import com.ksenia.demo.service.IProductTypeService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class ProductTypeServiceImpl implements IProductTypeService
{
	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<ProductType> getAllProductsType()
	{
		return productTypeRepository.findAll();
	}

	@Override
	public ProductType getProductTypeByID(Integer id)
	{
		return productTypeRepository.getOne(id);
	}

	@Override
	public void addProductType(ProductType productType)
	{
		productTypeRepository.saveAndFlush(productType);
	}

	@Override
	public ProductType editProduct(ProductType productType)
	{
		return productTypeRepository.saveAndFlush(productType);
	}

	@Override
	public void deleteProduct(Integer id)
	{
		productTypeRepository.deleteById(id);
	}

	@Override
	public Set<ProductType> getProductsTypeByCategoryName(String name)
	{
		return categoryRepository.findCategoryByName(name).getProductTypes();
	}

	@Override
	public ProductType getProductTypeByName(String name)
	{
		return productTypeRepository.getProductTypeByName(name);
	}
}
