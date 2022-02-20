/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Controller
public class IndexController
{
    @RequestMapping (value = "/index", method = RequestMethod.GET)
    public String showIndexPage (ModelMap model)
    {
        return "index";
    }

    @RequestMapping (value = "/payment/statement", method = RequestMethod.GET)
    public String showStatementPage (ModelMap model)
    {
        return "payment/statement";
    }
}
