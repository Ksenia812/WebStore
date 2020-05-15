package com.ksenia.demo.service;

import java.util.List;

import com.ksenia.demo.model.Booking;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public interface IBookingService
{
	Booking getBookingById(Integer id);
	List<Booking> getAllBookings();
	void addBooking(Booking booking);
	Booking editBooking(Booking booking);
	void deleteBooking(Integer id);
}
