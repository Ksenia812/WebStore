package com.ksenia.demo.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "booking")
@Data
public class Booking
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "booking_date")
	private Date date;
	@ToString.Exclude @EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ToString.Exclude @EqualsAndHashCode.Exclude
	@ManyToMany
	@JoinTable(name = "booking_products", joinColumns = @JoinColumn(name = "booking_id"),
		inverseJoinColumns = @JoinColumn(name = "product_id"))
	Set<Product> products;

	@Override
	public String toString()
	{
		return "Booking{" +
			"id=" + id +
			", date=" + date +
			'}';
	}
}
