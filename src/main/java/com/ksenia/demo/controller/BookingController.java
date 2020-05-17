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
import org.springframework.web.servlet.ModelAndView;

import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.Product;
import com.ksenia.demo.model.User;
import com.ksenia.demo.service.impl.BookingServiceImpl;
import com.ksenia.demo.service.impl.ProductServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class BookingController
{

	@Autowired
	private BookingServiceImpl bookingService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ProductServiceImpl productService;

	public static String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@GetMapping(value = "/products/add/{product}")
	public String addProduct(ModelAndView model, @PathVariable String product) {
		User user = userService.findUserByLogin(getCurrentUsername());
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
			booking.setUser(userService.findUserByLogin(getCurrentUsername()));
			bookingService.addBooking(booking);
			model.addObject("booking", booking);
		}
		model.setViewName("shopping_cart");
		return "redirect:/home/bookings";
	}

	@GetMapping(value = "/home/bookings")
	public String getBookings(Model model) {
		User user = userService.findUserByLogin(getCurrentUsername());
		Booking booking = user.getBookings().iterator().next();
		model.addAttribute("booking", booking);
		return "shopping_cart";
	}

	@GetMapping(value = "/booking/delete/{product}")
	public String deleteProductFromBooking(@PathVariable String product) {
//		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findUserByLogin(getCurrentUsername());
		Set<Product> products = user.getBookings().iterator().next().getProducts();
		Product deleteProduct = productService.getProductByName(product);
		products.remove(deleteProduct);
		Booking booking = user.getBookings().iterator().next();
		bookingService.editBooking(booking);
//		modelAndView.addObject(booking);
//		modelAndView.setViewName("shopping_cart");
		return "redirect:/home/bookings";
	}
}
