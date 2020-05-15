package com.ksenia.demo.service;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface ISecurityService
{
	String findLoggedInUsername();

	void autoLogin(String username, String password);
}
