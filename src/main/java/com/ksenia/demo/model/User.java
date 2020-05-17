package com.ksenia.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "user")
@Data
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String surname;
	private String login;
	private String password;
	private Integer active;
	private Double balance;
	@Transient
	String confirmPassword;
	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;
	@ManyToOne
	@JoinColumn(name = "black_list_id")
	private BlackList blackList;
	@OneToMany(mappedBy = "user")
	private Set<Booking> bookings;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@Override
	public String toString()
	{
		return "User{" +
			"id=" + id +
			", name='" + name + '\'' +
			", surname='" + surname + '\'' +
			", login='" + login + '\'' +
			", password='" + password + '\'' +
			", active=" + active +
			'}';
	}
}
