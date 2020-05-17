package com.ksenia.demo.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.Booking;
import com.ksenia.demo.model.User;
import com.ksenia.demo.service.impl.AddressServiceImpl;
import com.ksenia.demo.service.impl.UserServiceImpl;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class AddresController
{
	@Autowired
	private UserServiceImpl userService;

	@GetMapping(name = "/home/asd")
	public String getBooking(Model model) {
		User user = userService.findUserByLogin(getCurrentUsername());
		model.addAttribute("bookings", user.getBookings() != null ? user.getBookings(): new HashSet<Booking>());
		return "shopping_cart";
	}

	public static String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
