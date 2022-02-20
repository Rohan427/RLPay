/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.web.controller;

import com.randomlogic.rlpay.web.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
//@Controller
//@SessionAttributes ("name")
public class LoginController
{
    @Autowired
    LoginService service;

////    @RequestMapping (value="/login", method = RequestMethod.GET)
    public String showLoginPage (ModelMap model)
    {
        return "login";
    }

////    @RequestMapping (value="/login", method = RequestMethod.POST)
    public String showWelcomePage (ModelMap model, @RequestParam String name, @RequestParam String password){

        boolean isValidUser = true;

////        isValidUser = service.validateUser (name, password);

        if (!isValidUser)
        {
            model.put ("errorMessage", "Invalid Credentials");

            return "login";
        }

        model.put ("name", name);
        model.put ("password", password);

        return "welcome";
    }
}
