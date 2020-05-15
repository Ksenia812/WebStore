package com.ksenia.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.Role;
import com.ksenia.demo.repository.RoleRepository;
import com.ksenia.demo.service.IRoleService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class RoleServiceImpl implements IRoleService
{
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role getRoleById(Integer id)
	{
		return roleRepository.getOne(id);
	}

	@Override
	public List<Role> getAllRoles()
	{
		return roleRepository.findAll();
	}

	@Override
	public void addRole(Role role)
	{
		roleRepository.saveAndFlush(role);
	}

	@Override
	public Role editRole(Role role)
	{
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public void deleteRole(Integer id)
	{
		roleRepository.deleteById(id);
	}
}
