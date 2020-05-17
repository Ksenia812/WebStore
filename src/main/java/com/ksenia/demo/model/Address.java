package com.ksenia.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
@Entity
@Table(name = "address")
@Data
public class Address
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String town;
	private String street;
	private Integer houseNumber;
	private Integer flatNumber;
	@ToString.Exclude @EqualsAndHashCode.Exclude
	@OneToOne(mappedBy = "address")
	private User user;
}
