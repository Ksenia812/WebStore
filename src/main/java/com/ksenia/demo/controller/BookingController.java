package com.ksenia.demo.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.User;
import com.ksenia.demo.service.impl.BookingServiceImpl;
import com.ksenia.demo.service.impl.CategoryServiceImpl;
import com.ksenia.demo.service.impl.CustomUserDetailsService;
import com.ksenia.demo.service.impl.ProductServiceImpl;
import com.ksenia.demo.service.impl.ProductTypeServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;

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

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@GetMapping(value = "/products/add/{product}")
	public String addProduct(ModelAndView model, @PathVariable String product) {
		User user = userService.findUserByLogin(userDetailsService.getCurrentLogin());
		Product addProduct = productService.getProductByName(product);
		Booking bookingExist = bookingService.getBookingByUserId(user.getId());
		if (bookingExist != null && addProduct.getAmount() != 0) {
			Set<Product> products = bookingExist.getProducts();
			products.add(productService.getProductByName(product));
			bookingExist.setProducts(products);
			bookingService.editBooking(bookingExist);
			model.addObject("booking", bookingExist);
		} else if (addProduct.getAmount() != 0) {
			Booking booking = new Booking();
			Set<Product> products = new HashSet<>();
			products.add(productService.getProductByName(product));
			booking.setProducts(products);
			booking.setUser(userService.findUserByLogin(userDetailsService.getCurrentLogin()));
			bookingService.addBooking(booking);
			model.addObject("booking", booking);
		}
		model.setViewName("shopping_cart");
		return "redirect:/home/bookings";
	}

	@GetMapping(value = "/home/bookings")
	public String getBookings(Model model) {
		String currentUsername = userDetailsService.getCurrentLogin();
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
		Set<Booking> booking = userService.findUserByLogin(userDetailsService.getCurrentLogin()).getBookings();
		model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
		return "shopping_cart";
	}

	@GetMapping(value = "/booking/delete/{product}")
	public String deleteProductFromBooking(@PathVariable String product) {
//		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findUserByLogin(userDetailsService.getCurrentLogin());
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
		model.addAttribute("user", userService.findUserByLogin(userDetailsService.getCurrentLogin()));
		Set<Booking> booking = userService.findUserByLogin(userDetailsService.getCurrentLogin()).getBookings();
		model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
		return "payment";
	}

	@GetMapping(value = "/payment/pay")
	public String payForProduct(Model model) {
		model.addAttribute("productTypeByClothes", productTypeService.getProductsTypeByCategoryName(CLOTHES_TAB));
		model.addAttribute("productTypeByShoes", productTypeService.getProductsTypeByCategoryName(SHOES_TAB));
		model.addAttribute("productTypeByAccessories", productTypeService.getProductsTypeByCategoryName(ACCESSORIES_TAB));
		double balance = userService.findUserByLogin(userDetailsService.getCurrentLogin()).getBalance();
		User user = userService.findUserByLogin(userDetailsService.getCurrentLogin());
		Set<Product> products = user.getBookings().iterator().next().getProducts();
		if (balance == 0.0) {
			model.addAttribute("msg", "You do not have enough money");
			model.addAttribute("info", "alert alert-info");
			model.addAttribute("user", userService.findUserByLogin(userDetailsService.getCurrentLogin()));
			return "payment";
		} else {
			double finalCost=0;
			for (Product product : products) {
				finalCost+=product.getPrice();
			}
			if (user.getBalance() - finalCost < 0) {
				model.addAttribute("msg", "You do not have enough money to pay for your goods! Please add money.");
				model.addAttribute("msg", "alert alert-info");
				model.addAttribute("user", userService.findUserByLogin(userDetailsService.getCurrentLogin()));
				model.addAttribute("shoppingCart", userService.findUserByLogin(userDetailsService.getCurrentLogin()).getBookings().iterator().next().getProducts());
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
				model.addAttribute("info", "alert alert-success");
				model.addAttribute("user", userService.findUserByLogin(userDetailsService.getCurrentLogin()));
			}
		}
		return "payment";
	}
}
