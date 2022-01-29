/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.bean;

import com.randomlogic.rlpay.view.controller.impl.LoginController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable
{
    @Autowired
    private LoginController controller;

    private final static String DEVICEID = "12345";

    public void setControlller (LoginController controller)
    {
        this.controller = controller;
    }

    static public String getDeviceId()
    {
        return DEVICEID;
    }

    public void login()
    {
        controller.login();
    }

    public void register()
    {
        controller.register();
    }

    public void deleteAccount()
    {
        controller.deleteAccount();
    }

    public void setPassword (String password)
    {
        controller.setPassword (password);
    }

    public String getPassword()
    {
        return controller.getPassword();
    }

    public void setLoginId (String loginId)
    {
        controller.setLoginId (loginId);
    }

    public String getLoginId()
    {
        return controller.getLoginId();
    }

    public void setAccountNo (String accountNo)
    {
        controller.setAccountNo (accountNo);
    }

    public String getAccountNo()
    {
        return controller.getAccountNo();
    }

    public void setEmail (String email)
    {
        controller.setEmail (email);
    }

    public String getEmail()
    {
        return controller.getEmail();
    }

    public void setDescription (String description)
    {
        controller.setDescription (description);
    }

    public String getDescription()
    {
        return controller.getDescription();
    }
}
