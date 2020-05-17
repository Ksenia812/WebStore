package com.ksenia.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ksenia.demo.model.User;
import com.ksenia.demo.service.IUserService;

/**
 * Copyright (c) 2020 apollon GmbH+Co. KG All Rights Reserved.
 */

@Component
public class UserValidator implements Validator
{
	@Autowired
	private IUserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
		if (user.getName().length() < 8 || user.getName().length() > 32) {
			errors.rejectValue("username", "Size.userForm.username");
		}

		if (userService.findUserByName(user.getName()) != null) {
			errors.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
		if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "Different.userForm.password");
		}
	}
}
