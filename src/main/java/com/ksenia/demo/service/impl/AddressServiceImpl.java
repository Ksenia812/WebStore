package com.ksenia.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.repository.AddressRepository;
import com.ksenia.demo.service.IAddressService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class AddressServiceImpl implements IAddressService
{
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Address getAddressById(Integer id)
	{
		return addressRepository.getOne(id);
	}

	@Override
	public List<Address> getAllAddress()
	{
		return addressRepository.findAll();
	}

	@Override
	public void addAddress(Address address)
	{
		addressRepository.saveAndFlush(address);
	}

	@Override
	public Address editAddress(Address address)
	{
		return addressRepository.saveAndFlush(address);
	}

	@Override
	public void deleteAddress(Integer id)
	{
		addressRepository.deleteById(id);
	}
}
