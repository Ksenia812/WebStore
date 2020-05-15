package com.ksenia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ksenia.demo.service.IAddressService;
import com.ksenia.demo.service.impl.AddressServiceImpl;

@SpringBootApplication
public class WebStoreApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(WebStoreApplication.class, args);
	}

}
