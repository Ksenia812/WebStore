package com.ksenia.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "category")
@Data
public class Category
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@EqualsAndHashCode.Exclude @ToString.Exclude
	@OneToMany(mappedBy = "category")
	private Set<ProductType> productTypes;

	@Override
	public String toString()
	{
		return "Category{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
