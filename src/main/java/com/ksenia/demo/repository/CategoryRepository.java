package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.Category;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>
{
//	Category getCategoryById(Integer id);
	Category findCategoryByName(String name);
}
