package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.Product;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface ProductRepository extends JpaRepository<Product, Integer>
{
//	Goods getGoodsById(Integer id);
	Product findProductByName(String name);
}
