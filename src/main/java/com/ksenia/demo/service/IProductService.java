package com.ksenia.demo.service;

import java.util.List;
import java.util.Set;

import com.ksenia.demo.model.Product;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IProductService
{
	Product getGoodsById(Integer id);
	Product getProductByName(String name);
	List<Product> getAllProducts();
	void addProduct(Product product);
	Product editProduct(Product product);
	void deleteProduct(Integer id);
	Set<Product> getProductsByProductTypeName(String name);
}
