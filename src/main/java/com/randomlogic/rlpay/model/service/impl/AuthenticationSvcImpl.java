/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.application.util.Util;
import com.randomlogic.rlpay.model.domain.company.entity.APIClient;
import com.randomlogic.rlpay.model.domain.company.entity.Users;
import com.randomlogic.rlpay.model.domain.company.service.interfaces.ISSO;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.service.interfaces.IAPIUserAccessSvc;
import com.randomlogic.rlpay.model.service.interfaces.IAuthenticationSvc;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul.Allen
 */
@Service ("rlpaySvc")
public class AuthenticationSvcImpl implements IAuthenticationSvc
{
    private Collection<APIClient> clientList;

    @Autowired (required = true)
    private IAPIUserAccessSvc apiUsrSvc;

    @Autowired (required = true)
    private ISSO sso;

    private IAuthResponse ssoResponse;

    public void createTestUsers()
    {
        APIClient client;

        // Create list of test users
        clientList = new ArrayList<APIClient>();

        client = new APIClient ("pgallen", "12345", "pgallen", "pgallen@gmail.com", true);
        clientList.add (client);
    }

    /**
     *
     * @param client
     * @param password
     * @return the model.domain.interfaces.IClient
     */
    @Override
    public IClient login (IClient client, String password)
    {
        Users rlpayUser = new Users();

        // Client not authorized by default
        client.setAuthToken ("0");

        // Validate data exists
        if (client.getClientUID() != null &&
            client.getDeviceId() != null &&
            password != null
           )
        {
            rlpayUser.getId().setDeviceid (client.getDeviceId());
            rlpayUser.getId().setIdUser (0);
            rlpayUser.getId().setUid (client.getClientUID());

            rlpayUser = (Users) apiUsrSvc.find (rlpayUser);

            if (rlpayUser != null)
            {
                //TODO: Implement encryption
                if (password.equals (rlpayUser.getPassword()))
                {
                    if (rlpayUser.getValid () > 0)
                    {
                        Users glbUser = new Users();
                        glbUser.getId().setIdUser (rlpayUser.getId().getIdUser());
                        Logger.setUser (glbUser);
                        client.setAuthToken (Util.getGUID());
                    }
                    else
                    {
                        client.setAuthToken ("0");
                    }
                }
                else
                {
                    client.setAuthToken ("0");
                }
            }
            else
            {
                client.setAuthToken ("0");
            }

            rlpayUser = null;

            // For now, we'll return fake data to simulate a vaild login
////            do
////            {
////                rlClient = (APIClient)userIter.next();
////
////                if (client.getClientUID().equals (rlClient.getUid()))
////                {
////                    if (client.getDeviceId().equals (rlClient.getDeviceId()))
////                    {
////                        if (password.equals (rlClient.getPassword()))
////                        {
////                            client.setAuthToken (Util.getGUID()); // Client will use this to authenticate for next request
////                            result = true;
////                        }
////                        // else do nothing
////                    }
////                    // else do nothing
////                }
////                // else do nothing
////            } while (userIter.hasNext() && !result);
        }
        else
        {
            client.setAuthToken ("0");
        }

        return client;
    }

    /**
     *
     * @param user
     * @param password
     * @param email
     * @return
     */
    @Override
    public IUser register (IUser user, String password, String email)
    {
        boolean result = false;
        Iterator userIter = clientList.iterator();
        APIClient rlclient;

        // For now, we'll return fake data to simulate a vaild login
        do
        {
            rlclient = (APIClient)userIter.next();

            if (user.getUserName().equals (rlclient.getUid()))
            {
                if (password.equals (rlclient.getSecretKey()))
                {
                    if (user.getAccountNum().equals (rlclient.getDeviceId()))
                    {
                        if (email.equals (rlclient.getEmail()))
                        {
                            result = true;
                        }
                        // else Do nothing
                    }
                    // else Do nothing
                }
                // else Do nothing
            }
            // else Do nothing
        } while (userIter.hasNext() && !result);

        if (!result)
        {
            user.setAccountNum ("0");
        }
        // else Do nothing

        return user;
    }

    /**
     *
     * @param client
     * @param params
     * @return
     */
    @Override
    public IClient verify (IClient client, ServletParams params)
    {
        IClient lClient = params.getClient();
        String token;
        Users rlpayUser = new Users();

        try
        {
            if (lClient != null)
            {
                token = lClient.getAuthToken();

                if ((lClient.getClientUID().equals (client.getClientUID()))
                 && (lClient.getDeviceId().equals (client.getDeviceId()))
                 && (token.equals (client.getAuthToken()))
                   )
                {
                    lClient.setAuthToken (Util.getGUID());
                }
                // If failed, invalidate session and return invalid client
                else
                {
                    lClient = new Client();
                }
            }
            // No client if no login OR a customer connection
            else
            {
                lClient = new Client();
                String temppga = params.getCfg().getClientKey();
                String temprlpay = params.getCfg().getClientKey();

                rlpayUser.getId().setUid ("portaluser");
                rlpayUser.getId().setDeviceid (client.getDeviceId());

                rlpayUser = (Users) apiUsrSvc.find (rlpayUser);

                if (rlpayUser == null)
                {
                    rlpayUser = new Users();

                    rlpayUser.getId().setUid ("portaltest");
                    rlpayUser.getId().setDeviceid (client.getDeviceId());

                    rlpayUser = (Users) apiUsrSvc.find (rlpayUser);
                    lClient.setClientUID ("portaltest");
                }
                else
                {
                    lClient.setClientUID ("portaluser");
                }

                // Check for customer key
                if ((client.getDeviceId().equals (temppga))
                 || (client.getDeviceId().equals (temprlpay)))
                {
                    Users glbUser = new Users();
                    glbUser.getId().setIdUser (rlpayUser.getId().getIdUser());
                    Logger.setUser (glbUser);
                    lClient.setAuthToken (Util.getGUID());
                    lClient.setDeviceId (client.getDeviceId());
                }
                // else do nothing
            }
        }
        catch (NullPointerException e)
        {
            lClient = new Client();
        }

        params.setClient (lClient);
        return lClient;
    }
}