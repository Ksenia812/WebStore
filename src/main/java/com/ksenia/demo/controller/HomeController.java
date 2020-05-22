package com.ksenia.demo.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.service.impl.BookingServiceImpl;
import com.ksenia.demo.service.impl.CategoryServiceImpl;
import com.ksenia.demo.service.impl.ProductServiceImpl;
import com.ksenia.demo.service.impl.ProductTypeServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;


/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
@Controller
public class HomeController
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

	@Autowired
	private BookingServiceImpl bookingService;

	@GetMapping("/home")
	public String home(Model model)
	{
		addTabsToHeader(model);
		Set<Booking> booking = userService.findUserByLogin(getCurrentLogin()).getBookings();
		model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
		return "userhome";
	}

	public void addTabsToHeader(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
	}

	public String getCurrentLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@GetMapping(value = {"/clothes/{type}", "/shoes/{type}", "/accessories/{type}"})
	public String getClothesTab(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		addTabsToHeader(model);
		Set<Booking> booking = userService.findUserByLogin(getCurrentLogin()).getBookings();
		model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
		return "products";
	}

	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}


}
