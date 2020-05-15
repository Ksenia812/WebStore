/*
package com.ksenia.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ksenia.demo.service.ISecurityService;

*/
/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 *//*


@Service
public class SecurityServiceImpl implements ISecurityService
{

	//https://github.com/proselytear/SpringSecurityApp/tree/master/src/main/java/net/proselyte/springsecurityapp/service
	//https://www.youtube.com/watch?v=iivY8B5A0Tk
	private AuthenticationManager authenticationManager;

	private UserDetailsService userDetailsService;

	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	@Override
	public void autoLogin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(authenticationToken);

		if (authenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			System.out.println((String.format("Successfully %s auto logged in", username)));
		}
	}
}
*/
