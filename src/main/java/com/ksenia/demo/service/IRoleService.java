package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.Role;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IRoleService
{
	Role getRoleById(Integer id);
	List<Role> getAllRoles();
	void addRole(Role role);
	Role editRole(Role role);
	void deleteRole(Integer id);
}
