package com.ksenia.demo.controller;

import java.util.HashSet;
import java.util.Set;

import com.ksenia.demo.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.User;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class BookingController
{

	private static final String CLOTHES_TAB = "Clothes";
	private static final String SHOES_TAB = "Shoes";
	private static final String ACCESSORIES_TAB = "Accessories";

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private ProductTypeServiceImpl productTypeService;

	@Autowired
	private BookingServiceImpl bookingService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ProductServiceImpl productService;

	public static String getCurrentLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@GetMapping(value = "/products/add/{product}")
	public String addProduct(ModelAndView model, @PathVariable String product) {
		User user = userService.findUserByLogin(getCurrentLogin());
		Booking bookingExist = bookingService.getBookingByUserId(user.getId());
		if (bookingExist != null) {
			Set<Product> products = bookingExist.getProducts();
			products.add(productService.getProductByName(product));
			bookingExist.setProducts(products);
			bookingService.editBooking(bookingExist);
			model.addObject("booking", bookingExist);
		} else {
			Booking booking = new Booking();
			Set<Product> products = new HashSet<>();
			products.add(productService.getProductByName(product));
			booking.setProducts(products);
			booking.setUser(userService.findUserByLogin(getCurrentLogin()));
			bookingService.addBooking(booking);
			model.addObject("booking", booking);
		}
		model.setViewName("shopping_cart");
		return "redirect:/home/bookings";
	}

	@GetMapping(value = "/home/bookings")
	public String getBookings(Model model) {
		String currentUsername = getCurrentLogin();
		User user = userService.findUserByLogin(currentUsername);
		if (user.getBookings().iterator().hasNext()) {
			Booking booking = user.getBookings().iterator().next();
			model.addAttribute("booking", booking);

		} else {
			model.addAttribute("booking", new Booking());
		}
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		return "shopping_cart";
	}

	@GetMapping(value = "/booking/delete/{product}")
	public String deleteProductFromBooking(@PathVariable String product) {
//		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findUserByLogin(getCurrentLogin());
		Set<Product> products = user.getBookings().iterator().next().getProducts();
		Product deleteProduct = productService.getProductByName(product);
		products.remove(deleteProduct);
		Booking booking = user.getBookings().iterator().next();
		bookingService.editBooking(booking);
//		modelAndView.addObject(booking);
//		modelAndView.setViewName("shopping_cart");
		return "redirect:/home/bookings";
	}

	@GetMapping(value = "/payment")
	public String goToPayment(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		model.addAttribute("user", userService.findUserByLogin(getCurrentLogin()));
		return "payment";
	}

	@GetMapping(value = "/payment/pay")
	public String payForProduct(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		double balance = userService.findUserByLogin(getCurrentLogin()).getBalance();
		User user = userService.findUserByLogin(getCurrentLogin());
		Set<Product> products = user.getBookings().iterator().next().getProducts();
		if (balance == 0.0) {
			model.addAttribute("msg", "You do not have enough money");
			model.addAttribute("user", userService.findUserByLogin(getCurrentLogin()));
			return "payment";
		} else {
			double finalCost=0;
			for (Product product : products) {
				finalCost+=product.getPrice();
			}
			if (user.getBalance() - finalCost < 0) {
				model.addAttribute("msg", "You do not have enough money to pay for your goods! Please add money.");
				model.addAttribute("user", userService.findUserByLogin(getCurrentLogin()));
				return "payment";
			} else {
				user.setBalance(user.getBalance() - finalCost);
				for (Product product : products) {
					product.setAmount(product.getAmount() - 1);
					productService.editProduct(product);
				}
				products.clear();
				Booking booking = user.getBookings().iterator().next();
				bookingService.editBooking(booking);
				userService.editUser(user);
				bookingService.editBooking(user.getBookings().iterator().next());
				model.addAttribute("msg", "Your operation was successful.Wait for your purchases!");
				model.addAttribute("user", userService.findUserByLogin(getCurrentLogin()));
			}
		}
		return "payment";
	}
}
