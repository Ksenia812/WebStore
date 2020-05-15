package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.Role;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface RoleRepository extends JpaRepository<Role, Integer>
{
//	Role findByRole(String role);
}
