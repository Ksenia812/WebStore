package com.ksenia.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.Category;
import com.ksenia.demo.repository.CategoryRepository;
import com.ksenia.demo.service.ICategoryService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class CategoryServiceImpl implements ICategoryService
{

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Integer id)
	{
		return categoryRepository.getOne(id);
	}

	@Override
	public List<Category> getAllCategory()
	{
		return categoryRepository.findAll();
	}

	@Override
	public void addCategory(Category category)
	{
		categoryRepository.saveAndFlush(category);
	}

	@Override
	public Category editCategory(Category category)
	{
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public void deleteCategory(Integer id)
	{
		categoryRepository.deleteById(id);
	}

	@Override
	public Category getCategoryByName(String name)
	{
		return categoryRepository.findCategoryByName(name);
	}
}
