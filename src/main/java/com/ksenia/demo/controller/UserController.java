package com.ksenia.demo.controller;

import javax.validation.Valid;

import com.ksenia.demo.model.Booking;
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
import com.ksenia.validator.UserValidator;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAddressService addressService;

    public static String getCurrentLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("user/signup");

        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByLogin(user.getLogin());

        if (userExists != null) {
            bindingResult.rejectValue("login", "error.user", "This login already exists!");
        }
        if (!bindingResult.hasErrors()) {
            userService.addUser(user);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("user", new User());
        }
        model.setViewName("user/signup");

        return model;
    }

    @GetMapping(value = "/home/userprofile")
    public String getUserProfile(Model model) {
        model.addAttribute("user", userService.findUserByLogin(getCurrentLogin()));
        Set<Booking> booking = userService.findUserByLogin(getCurrentLogin()).getBookings();
        model.addAttribute("shoppingCart", !booking.isEmpty() ? booking.iterator().next().getProducts() : new HashSet<>());
        return "user_profile";
    }

    @PostMapping(value = "/home/userprofile/save")
    public ModelAndView saveUserProfile(ModelAndView model, @Valid User user) {
//        String currentLogin= getCurrentLogin();
        User editUser = userService.findUserByLogin(getCurrentLogin());
        /*User userExists = userService.findUserByLogin(user.getLogin());
        if (userExists != null) {
            model.addObject("msg", "This login already exists!");
            model.setViewName("user_profile");
            return model;
        } else {*/
        editUser.setName(user.getName());
        editUser.setSurname(user.getSurname());
        editUser.setLogin(user.getLogin());
        Address editAddress = editUser.getAddress();
        editUser.getAddress().setTown(user.getAddress().getTown());
        editUser.getAddress().setStreet(user.getAddress().getStreet());
        editUser.getAddress().setHouseNumber(user.getAddress().getHouseNumber());
        editUser.getAddress().setFlatNumber(user.getAddress().getFlatNumber());
        editUser.setAddress(editAddress);
        addressService.editAddress(editAddress);
        userService.editUser(editUser);
        model.addObject("msg", "User profile was changed");
        model.setViewName("user_profile");
//        }
        return model;
    }

/*
	@RequestMapping(value= {"/home/home"}, method=RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByLogin(auth.getName());

		model.addObject("userName", user.getName() + " " + user.getSurname());
		model.setViewName("home/home");
		return model;
	}

	@RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/access_denied");
		return model;
	}
*/







/*	@Autowired
	private IUserService userService;

	@Autowired
	private ISecurityService securityService;

	private final UserValidator userValidator = new UserValidator();

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.addUser(userForm);

		securityService.autoLogin(userForm.getName(), userForm.getConfirmPassword());

		return "redirect:/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Username or password is incorrect.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out successfully.");
		}

		return "login";
	}

	@RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "welcome";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
		return "admin";
	}*/
}
