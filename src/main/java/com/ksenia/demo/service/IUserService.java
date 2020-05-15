package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.User;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IUserService
{
	User getUserById(Integer id);
	User findUserByLogin(String name);
	List<User> getAllUsers();
	void addUser(User user);
	User editUser(User user);
	void deleteUser(Integer id);
}
