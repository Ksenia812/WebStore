package com.ksenia.demo.controller;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.User;
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

	@Autowired
	private BookingServiceImpl bookingService;

	@GetMapping("/home")
	public String home(Model model)
	{
//		model.addAttribute("msgs", messageRepository.findAll());
		User user = userService.findUserByLogin(getCurrentUsername());
		model.addAttribute("bookings", user.getBookings());
		return "userhome";
	}

	public static String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
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

	@GetMapping(value = {"/clothes/{type}", "/shoes/{type}", "/accessories/{type}"})
	public String getClothesTab(Model model, @PathVariable String type) {
		model.addAttribute("products", productService.getProductsByProductTypeName(type));
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "products";
	}

	@GetMapping(value = "/products/add/{product}")
	public ModelAndView addProduct(ModelAndView model, @PathVariable String product) {
		User user = userService.findUserByLogin(getCurrentUsername());
		Booking bookingExist = bookingService.getBookingByUserId(user.getId());
		if (bookingExist != null) {
			Set<Product> products = bookingExist.getProducts();
			products.add(productService.getProductByName(product));
			bookingExist.setProducts(products);
			bookingService.editBooking(bookingExist);
			model.addObject("bookings", bookingExist);
		} else {
			Booking booking = new Booking();
			Set<Product> products = new HashSet<>();
			products.add(productService.getProductByName(product));
			booking.setProducts(products);
			booking.setUser(userService.findUserByLogin(getCurrentUsername()));
			bookingService.addBooking(booking);
			model.addObject("bookings", booking);
		}
		model.setViewName("shopping_cart");
		return model;
	}

/*
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
	}*/

/*
	@GetMapping(value = "booking/{product}")
	public String orderBooking(Model model, @PathVariable String product) {
		model.addAttribute("products", userService.findUserByLogin("dsa").getBookings().iterator().next().getProducts());
		return "booking";
	}
*/

/*	@GetMapping(value = "/*")
	public String getHeader(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "fragments/header";
	}*/

	@GetMapping(value = "/login")
	public String loginPage() {
		return "login";
	}
/*	@PostMapping("/messages")
	public String saveMessage(Message message)
	{
		messageRepository.save(message);
		return "redirect:/home";
	}*/
}
