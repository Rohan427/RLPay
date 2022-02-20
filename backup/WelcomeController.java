package com.randomlogic.rlpay.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;

////@Controller
public class WelcomeController
{
////	@RequestMapping (value = "/", method = RequestMethod.GET)
	public String showWelcomePage (ModelMap model)
    {
		model.put ("name", getLoggedinUserName());

		return "welcome";
	}

	private String getLoggedinUserName()
    {
		Object principal = SecurityContextHolder.getContext()
                                                .getAuthentication().getPrincipal();

		if (principal instanceof UserDetails)
        {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}

}