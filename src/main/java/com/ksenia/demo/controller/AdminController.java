package com.ksenia.demo.controller;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Category;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.ProductType;
import com.ksenia.demo.model.User;

import com.ksenia.demo.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ksenia.demo.service.impl.CategoryServiceImpl;
import com.ksenia.demo.service.impl.ProductServiceImpl;
import com.ksenia.demo.service.impl.ProductTypeServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;

import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class AdminController
{
	private static Logger logger = Logger.getLogger(AdminController.class.getName());

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ProductTypeServiceImpl productTypeService;

	@Autowired
	private UserServiceImpl userService;

	@GetMapping(value = "/admin/users")
	public String getUsers(Model model)
	{
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}

	@GetMapping(value = "/admin/products")
	public String getAllProducts(Model model)
	{
		model.addAttribute("products", productService.getAllProducts());
		return "admin_products";
	}

	@GetMapping(value = "/admin/product/delete/{id}")
	public String deleteProduct(Model model, @PathVariable Integer id)
	{
		try
		{
			productService.deleteProduct(id);
		}
		catch (Exception ex)
		{
			logger.warning("This product can't be removed");
			System.out.println("This product can't be removed cause this product is edited to shopping cart!");
		}
		return "redirect:/admin/products";
	}

	@GetMapping(value = "/admin/product/edit/{id}")
	public String getProductInfo(Model model, @PathVariable Integer id)
	{
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("types", productTypeService.getProductsTypeByCategoryName(product.getType().getCategory().getName()));
		return "edit_product";
	}

	@GetMapping(value = "/admin/product")
	public @ResponseBody Set<ProductType> getProductTypeByCategoryName(ModelAndView model, @RequestParam(value = "categoryName") String name) throws IOException {
		return productTypeService.getProductsTypeByCategoryName(name);
	}

	@PostMapping(value = "/admin/product/edit_save/{id}")
	public String editProductInfo(ModelAndView model, @PathVariable Integer id,  @Valid Product product, BindingResult bindingResult)
	{
		if (!bindingResult.hasErrors()) {
			Product editProduct = product;
			editProduct.setId(id);
			ProductType type = productTypeService.getProductTypeByName(product.getType().getName());
			editProduct.setType(type);
			productService.addProduct(editProduct);
			logger.info("Product was edited");
			model.addObject("msg", "Operation was successful!");
			model.setViewName("edit_product");
			return "redirect:/admin/products";
//        }
		}
		model.setViewName("edit_product");
		model.addObject("product", product);
		model.addObject("msg", "Error");
		logger.warning("Product can't be edited");
		return "edit_product";
	}

	@GetMapping(value = "/admin/product/add")
	public ModelAndView addProductView(ModelAndView model)
	{
		model.addObject("product", new Product());
		model.setViewName("add_product");
		return model;
	}

	@PostMapping(value = "/admin/product/add")
	public ModelAndView addProduct(ModelAndView model, @Valid Product product, BindingResult bindingResult)
	{
		Category categoryExist = categoryService.getCategoryByName(product.getType().getCategory().getName());
		ProductType typeExist = productTypeService.getProductTypeByName(product.getType().getName());
		if (!bindingResult.hasErrors())
		{
			if (categoryExist != null)
			{
				if (categoryExist.getProductTypes().contains(typeExist))
				{
					product.setType(typeExist);
				}
				else
				{
					ProductType addType = product.getType();
					addType.setCategory(categoryExist);
					product.setType(addType);
					productTypeService.addProductType(product.getType());
				}
			}
			else
			{
				categoryService.addCategory(product.getType().getCategory());
				productTypeService.addProductType(product.getType());
			}
			productService.addProduct(product);
			logger.info("Product was added");
			model.setViewName("redirect:/admin/products");
			return model;
		}
		model.setViewName("add_product");
		return model;
	}
}
