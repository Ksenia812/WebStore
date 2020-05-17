package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.User;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface UserRepository extends JpaRepository<User, Integer>
{
//	User getUserById(Integer id);
	User findUserByName(String name);
	User findUserByLogin(String login);
}
