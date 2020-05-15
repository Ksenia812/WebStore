package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.ProductType;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>
{
	ProductType getProductTypeByName(String name);
}
