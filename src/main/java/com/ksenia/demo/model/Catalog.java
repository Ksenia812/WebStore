package com.ksenia.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Deprecated
@Entity
//@Table(name = "catalog")
public class Catalog
{
	@Id
	private int id;
}
