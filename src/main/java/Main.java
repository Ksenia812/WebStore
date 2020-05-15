import com.ksenia.demo.service.IAddressService;
import com.ksenia.demo.service.impl.AddressServiceImpl;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */
public class Main
{
	public static void main(String[] args)
	{
		IAddressService addressService = new AddressServiceImpl();
		System.out.println(addressService.getAddressById(1));
	}
}
