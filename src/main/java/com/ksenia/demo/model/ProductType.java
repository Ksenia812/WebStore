package com.ksenia.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "product_type")
@JsonIgnoreProperties({"category", "products"})
@Data
public class ProductType
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@EqualsAndHashCode.Exclude @ToString.Exclude
	@OneToMany(mappedBy = "type")
	private Set<Product> products;

	@Override
	public String toString()
	{
		return "ProductType{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
