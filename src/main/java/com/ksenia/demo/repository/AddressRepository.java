package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.User;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>
{
}
