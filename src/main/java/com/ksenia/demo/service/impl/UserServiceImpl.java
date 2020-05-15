package com.ksenia.demo.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.Role;
import com.ksenia.demo.model.User;
import com.ksenia.demo.repository.AddressRepository;
import com.ksenia.demo.repository.RoleRepository;
import com.ksenia.demo.repository.UserRepository;
import com.ksenia.demo.service.IUserService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class UserServiceImpl implements IUserService
{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AddressRepository addressRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public User getUserById(Integer id)
	{
		return userRepository.getOne(id);
	}

	@Override
	public User findUserByLogin(String name)
	{
		return userRepository.findUserByLogin(name);
	}

	@Override
	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}

	@Override
	public void addUser(User user)
	{
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.getOne(1));
		user.setRoles(roles);
		user.setActive(2);
/*		Address address = new Address();
		address.setTown("asd");
		address.setStreet("asd");
		address.setHouseNumber(2);
		address.setFlatNumber(1);*/
		addressRepository.saveAndFlush(user.getAddress());
		userRepository.saveAndFlush(user);
	}

	@Override
	public User editUser(User user)
	{
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(Integer id)
	{
		userRepository.deleteById(id);
	}
}
