package com.ksenia.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksenia.demo.model.Address;
import com.ksenia.demo.model.Booking;
import com.ksenia.demo.repository.BookingRepository;
import com.ksenia.demo.service.IBookingService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Service
public class BookingServiceImpl implements IBookingService
{

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public Booking getBookingById(Integer id)
	{
		return bookingRepository.getOne(id);
	}

	@Override
	public List<Booking> getAllBookings()
	{
		return bookingRepository.findAll();
	}

	@Override
	public void addBooking(Booking booking)
	{
		bookingRepository.saveAndFlush(booking);
	}

	@Override
	public Booking editBooking(Booking booking)
	{
		return bookingRepository.saveAndFlush(booking);
	}

	@Override
	public void deleteBooking(Integer id)
	{
		bookingRepository.deleteById(id);
	}
}
