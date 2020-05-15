package com.ksenia.demo.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Entity
@Table(name = "role")
@Data
public class Role
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
/*	@ManyToMany(mappedBy = "roles")
	private Set<User> users;*/

	@Override
	public String toString()
	{
		return "Role{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
