package com.ksenia.demo.controller;

import javax.validation.Valid;

import com.ksenia.demo.model.Booking;

import com.ksenia.demo.service.IProductTypeService;
import com.ksenia.demo.service.impl.ProductTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.User;
import com.ksenia.demo.service.IAddressService;
import com.ksenia.demo.service.ISecurityService;
import com.ksenia.demo.service.IUserService;
import com.ksenia.demo.service.impl.CustomUserDetailsService;
import com.ksenia.validator.UserValidator;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class UserController
{

	private static Logger logger = Logger.getLogger(UserController.class.getName());

	@Autowired
	private IUserService userService;

	@Autowired
	private IAddressService addressService;

	@Autowired
	private ProductTypeServiceImpl productTypeService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public ModelAndView signup()
	{
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user", user);
		model.setViewName("user/signup");

		return model;
	}

	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	public String createUser(@Valid User user, BindingResult bindingResult)
	{
		ModelAndView model = new ModelAndView();
		User userExists = userService.findUserByLogin(user.getLogin());

		if (userExists != null)
		{
			logger.info("This login already exists");
			bindingResult.rejectValue("login", "error.user", "This login already exists!");
		}
		if (!bindingResult.hasErrors())
		{
			logger.info("User has been registered");
			userService.addUser(user);
			model.addObject("msg", "User has been registered successfully!");
		}
		model.setViewName("user/signup");

		return "redirect:/login";
	}

	@GetMapping(value = "/home/userprofile")
	public String getUserProfile(Model model)
	{
		String login = userDetailsService.getCurrentLogin();
		model.addAttribute("user", userService.findUserByLogin(login));
		Set<Booking> booking = userService.findUserByLogin(login).getBookings();
		productTypeService.addAllTypeForTabs(model);
		model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
		return "user_profile";
	}

	@PostMapping(value = "/home/userprofile/save")
	public ModelAndView saveUserProfile(ModelAndView model, @Valid User user, BindingResult bindingResult)
	{
		User editUser = userService.findUserByLogin(userDetailsService.getCurrentLogin());
		if (!bindingResult.hasErrors())
		{
			editUser.setName(user.getName());
			editUser.setSurname(user.getSurname());
			editUser.setLogin(user.getLogin());
			editUser.setBalance(editUser.getBalance() + user.getBalance());
			Address editAddress = editUser.getAddress();
			editUser.getAddress().setTown(user.getAddress().getTown());
			editUser.getAddress().setStreet(user.getAddress().getStreet());
			editUser.getAddress().setHouseNumber(user.getAddress().getHouseNumber());
			editUser.getAddress().setFlatNumber(user.getAddress().getFlatNumber());
			editUser.setAddress(editAddress);
			addressService.editAddress(editAddress);
			userService.editUser(editUser);
			logger.info("User profile was changed");
			model.addObject("msg", "User profile was changed");
		}
		model.setViewName("user_profile");
		return model;
	}








}
