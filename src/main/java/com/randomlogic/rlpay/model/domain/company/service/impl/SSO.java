/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.service.impl;

import com.randomlogic.rlpay.application.config.Config;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.company.service.interfaces.ISSO;
import com.randomlogic.rlpay.resources.api.azure.interfaces.IAuthenticate;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Paul.Allen
 */
@Controller ("sso")
public class SSO implements ISSO, Serializable
{
    private static final long serialVersionUID = 6116481192850706213L;

    @Autowired (required = true)
    private IAuthenticate authenticate;

    private IAuthParams authParams = new AuthParams();
    private IAuthResponse response;
    private Configuration clientId = new Configuration();
    private Configuration authority = new Configuration();
    private Configuration tenant = new Configuration();
    private Configuration secret = new Configuration();
    private Config cfgBean;
    private Configuration enabled = new Configuration();

    @Override
    public void init (ServletParams params)
    {
        String message = "";

        cfgBean = (Config)params.getContext().getAttribute ("cfgBean");

        try
        {
            message = "ssoMode";
            enabled.setCfgvalue (cfgBean.getConfig ("ssoMode"));
            authParams.setEnabled (cfgBean.getConfig ("ssoMode"));

            if (isEnabled())
            {
                message = "azureTenantId";
                tenant.setCfgvalue (cfgBean.getConfig ("azureTenantId"));
                authParams.setTenant (cfgBean.getConfig ("azureTenantId"));

                message = "azureClientId";
                clientId.setCfgvalue (cfgBean.getConfig ("azureClientId"));
                authParams.setClientId (cfgBean.getConfig ("azureTenantId"));

                message = "azureAuthority";
                authority.setCfgvalue (cfgBean.getConfig ("azureAuthority"));
                authParams.setAuthority (cfgBean.getConfig ("azureAuthority"));

                message = "azureSecret";
                secret.setCfgvalue (cfgBean.getConfig ("azureSecret"));
                authParams.setSecretKey (cfgBean.getConfig ("azureSecret"));
            }
            // else do nothing

            authParams.setRequest (params.getRequest());
            authParams.setResponse (params.getResponse());

            authenticate.init (authParams);
        }
        catch (NullPointerException e)
        {
            Logger.log (Logger.EXCEPTION, SSO.class, e, ".SSO: Null " + message);
        }
    }

    @Override
    public boolean authenticate()
    {
        response = authenticate.authenticate();

        return response.isValid();
    }

    @Override
    public IAuthResponse getResponse()
    {
        return this.response;
    }

    @Override
    public boolean isEnabled()
    {
        if (enabled.getCfgvalue().equals ("enabled"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
