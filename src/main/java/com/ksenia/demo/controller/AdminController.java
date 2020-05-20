package com.ksenia.demo.controller;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ksenia.demo.service.impl.CategoryServiceImpl;
import com.ksenia.demo.service.impl.ProductServiceImpl;
import com.ksenia.demo.service.impl.ProductTypeServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class AdminController
{
	private static final String CLOTHES_TAB = "Clothes";
	private static final String SHOES_TAB = "Shoes";
	private static final String ACCESSORIES_TAB = "Accessories";

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ProductTypeServiceImpl productTypeService;

	@Autowired
	private UserServiceImpl userService;

	//admin/products/sneakers
/*	@GetMapping(value = "/admin/products/{type}")
	public String addProduct(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "admin_products";
	}*/
	@GetMapping(value = "/admin/users")
		public String getUsers(Model model)
	{model.addAttribute("users",userService.getAllUsers());
		return "users";
	}
	@GetMapping(value = "/admin/products")
	public String getAllProducts(Model model){
		model.addAttribute("products",productService.getAllProducts());
		return "admin_products";
	}

	@GetMapping(value = "/admin/product/delete/{id}")
	public String deleteProduct(Model model, @PathVariable Integer id){
		try {
			productService.deleteProduct(id);
		} catch (Exception ex) {
			System.out.println("This product can't be removed cause this product is edited to shopping cart!");
		}
		return "redirect:/admin/products";
	}

	@GetMapping(value = "/admin/product/edit/{id}")
	public String getProductInfo(Model model, @PathVariable Integer id) {
		model.addAttribute("product", productService.getProductById(id));
		model.addAttribute("types",productTypeService.getAllProductsType());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "edit_product";
	}

	@PostMapping(value = "/admin/product/edit")
	public ModelAndView editProductInfo(ModelAndView model, @Valid Product product) {
		Product editProduct = product;
		model.addObject("msg", "Operation was successful!");
		model.setViewName("edit_product");
//        }
		return model;
	}

	@GetMapping(value = "/admin/product/add")
	public ModelAndView addProductView(ModelAndView model) {
		model.addObject("product", new Product());
		model.setViewName("add_product");
		return model;
	}

	@PostMapping(value = "/admin/product/add")
	public String addProduct(ModelAndView model, @Valid Product product) {
		categoryService.addCategory(product.getType().getCategory());
		productTypeService.addProductType(product.getType());
		productService.addProduct(product);
		return "redirect:/admin/products";
	}
}
