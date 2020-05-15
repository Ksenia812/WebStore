package com.ksenia.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.Catalog;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface CatalogRepository extends JpaRepository<Catalog, Integer>
{
}
