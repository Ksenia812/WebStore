package com.ksenia.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "product")
@Data
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@PositiveOrZero
	private double price;
	@PositiveOrZero
	private int amount;
	private String description;
	@ToString.Exclude @EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "product_type_id")
	private ProductType type;

	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToMany(mappedBy = "products")
	private Set<Booking> bookings;

	@Override
	public String toString()
	{
		return "Product{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", amount=" + amount +
			", description='" + description + '\'' +
			'}';
	}
}
