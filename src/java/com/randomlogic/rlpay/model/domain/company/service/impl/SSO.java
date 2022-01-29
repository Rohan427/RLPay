/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.service.impl;

import java.io.Serializable;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.company.service.interfaces.ISSO;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.Ipersist;
import com.randomlogic.rlpay.resources.api.azure.facade.Authenticate;
import com.randomlogic.rlpay.resources.api.azure.interfaces.IAuthenticate;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Paul.Allen
 */
@Controller
public class SSO implements ISSO, Serializable
{
    private static final long serialVersionUID = 6116481192850706213L;

    private IAuthenticate authenticate;
    private IAuthParams authParams = new AuthParams();
    private IAuthResponse response;
    private Configuration clientId = new Configuration();
    private Configuration authority = new Configuration();
    private Configuration tenant = new Configuration();
    private Configuration secret = new Configuration();
    private Ipersist configDao;
    private Configuration enabled = new Configuration();

    public SSO()
    {
        authenticate = new Authenticate();
    }

    @Override
    public void init (ServletParams params)
    {
        configDao = new ConfigAccessSvcImpl();
        String message = "";

        try
        {
            message = "ssoMode";
            enabled.getId().setConfigKey ("ssoMode");
            enabled = (Configuration)configDao.find (enabled);
            authParams.setEnabled (enabled.getValue());

            if (isEnabled())
            {
                message = "azureTenantId";
                tenant.getId().setConfigKey ("azureTenantId");
                tenant = (Configuration)configDao.find (tenant);
                authParams.setTenant (tenant.getValue());

                message = "azureClientId";
                clientId.getId().setConfigKey ("azureClientId");
                clientId = (Configuration)configDao.find (clientId);
                authParams.setClientId (clientId.getValue());

                message = "azureAuthority";
                authority.getId().setConfigKey ("azureAuthority");
                authority = (Configuration)configDao.find (authority);
                authParams.setAuthority (authority.getValue());

                message = "azureSecret";
                secret.getId().setConfigKey ("azureSecret");
                secret = (Configuration)configDao.find (secret);
                authParams.setSecretKey (secret.getValue());
            }
            // else do nothing

            authParams.setRequest (params.getRequest());
            authParams.setResponse (params.getResponse());

            authenticate.init (authParams);
        }
        catch (NullPointerException e)
        {
            Logger.log (0, SSO.class, e, ".SSO: Null " + message);
        }
        finally
        {
            configDao.close();
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
        if (enabled.getValue().equals ("enabled"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
