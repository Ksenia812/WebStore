package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.Category;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface ICategoryService
{
	Category getCategoryById(Integer id);
	List<Category> getAllCategory();
	void addCategory(Category category);
	Category editCategory(Category category);
	void deleteCategory(Integer id);
	Category getCategoryByName(String name);
}
