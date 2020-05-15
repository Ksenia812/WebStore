package com.ksenia.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.service.impl.AddressServiceImpl;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Controller
public class AddresController
{

	@Autowired
	private AddressServiceImpl addressService;

	@RequestMapping(value = "/address")
	public String showCities() {
		StringBuilder result = new StringBuilder();

		for (Address address : addressService.getAllAddress()) {
			result.append(address);
		}

		return result.toString();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute Address address) {
		return "blog";
	}
}
