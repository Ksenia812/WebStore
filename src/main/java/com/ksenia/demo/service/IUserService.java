package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.User;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IUserService
{
	User getUserById(Integer id);
	User findUserByName(String name);
	User findUserByLogin(String login);
	List<User> getAllUsers();
	void addUser(User user);
	User editUser(User user);
	void deleteUser(Integer id);
}
