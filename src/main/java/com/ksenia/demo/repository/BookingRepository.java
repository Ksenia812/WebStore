package com.ksenia.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ksenia.demo.model.Booking;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface BookingRepository extends JpaRepository<Booking, Integer>
{
}
