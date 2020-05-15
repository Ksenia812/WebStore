package com.ksenia.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
//	@Autowired
//	private MessageRepository messageRepository;

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

	@GetMapping("/home")
	public String home(Model model)
	{
//		model.addAttribute("msgs", messageRepository.findAll());
		return "userhome";
	}

/*	@GetMapping(value = {"/"})
	public String navBar(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "fragments/header";
	}*/

/*	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}*/

	@GetMapping(value = "/clothes/{type}")
	public String getClothesTab(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "products";
	}

	@GetMapping(value = "/shoes/{type}")
	public String getShoesTab(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "products";
	}

	@GetMapping(value = "/accessories/{type}")
	public String getAccessoriesTab(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "products";
	}

	@GetMapping(value = "booking/{product}")
	public String orderBooking(Model model, @PathVariable String product) {
		model.addAttribute("products", userService.findUserByLogin("dsa").getBookings().iterator().next().getProducts());
		return "booking";
	}
/*	@PostMapping("/messages")
	public String saveMessage(Message message)
	{
		messageRepository.save(message);
		return "redirect:/home";
	}*/
}
