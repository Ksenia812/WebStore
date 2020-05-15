package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.Address;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IAddressService
{
	Address getAddressById(Integer id);
	List<Address> getAllAddress();
	void addAddress(Address address);
	Address editAddress(Address address);
	void deleteAddress(Integer id);
}
