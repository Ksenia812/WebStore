package com.ksenia.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.PositiveOrZero;

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
	@Digits(integer = 3, fraction = 0, message = "More then 3 digits")
	@PositiveOrZero
	private Integer houseNumber;
	@Digits(integer = 3, fraction = 0, message = "More then 3 digits")
	@PositiveOrZero
	private Integer flatNumber;
	@ToString.Exclude @EqualsAndHashCode.Exclude
	@OneToOne(mappedBy = "address")
	private User user;
}
