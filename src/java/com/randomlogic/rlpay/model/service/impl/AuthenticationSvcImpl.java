/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.application.util.Util;
import com.randomlogic.rlpay.model.domain.company.entity.APIClient;
import com.randomlogic.rlpay.model.domain.company.entity.User;
import com.randomlogic.rlpay.model.domain.company.service.impl.SSO;
import com.randomlogic.rlpay.model.domain.company.service.interfaces.ISSO;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.service.interfaces.IAPIUserAccessSvc;
import com.randomlogic.rlpay.model.service.interfaces.IAuthenticationSvc;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul.Allen
 */
@Service
public class AuthenticationSvcImpl implements IAuthenticationSvc
{
    private Collection<APIClient> clientList;
    private IAPIUserAccessSvc apiUserSvc;

////    @Autowired
    private ISSO sso;
    private IAuthResponse ssoResponse;

    public AuthenticationSvcImpl()
    {
        APIClient client;

        apiUserSvc = new APIUserAccessSvcImpl();

        // Create list of test users
        clientList = new ArrayList<APIClient>();

        client = new APIClient ("pgallen", "12345", "pgallen", "pgallen@gmail.com", true);
        clientList.add (client);

        sso = new SSO();
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
        User dccUser = new User();

        // Client not authorized by default
        client.setAuthToken ("0");

        // Validate data exists
        if (client.getClientUID() != null &&
            client.getDeviceId() != null &&
            password != null
           )
        {
            dccUser.getId().setDeviceid (client.getDeviceId());
            dccUser.getId().setIdUser (0);
            dccUser.getId().setUid (client.getClientUID());

            dccUser = (User) apiUserSvc.find (dccUser);

            if (dccUser != null)
            {
                //TODO: Implement encryption
                if (password.equals (dccUser.getPassword()))
                {
                    if (dccUser.getValid () > 0)
                    {
                        User glbUser = new User();
                        glbUser.getId().setIdUser (dccUser.getId().getIdUser());
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

            dccUser = null;

            // For now, we'll return fake data to simulate a vaild login
////            do
////            {
////                dccClient = (APIClient)userIter.next();
////
////                if (client.getClientUID().equals (dccClient.getUid()))
////                {
////                    if (client.getDeviceId().equals (dccClient.getDeviceId()))
////                    {
////                        if (password.equals (dccClient.getPassword()))
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
        APIClient dccclient;

        // For now, we'll return fake data to simulate a vaild login
        do
        {
            dccclient = (APIClient)userIter.next();

            if (user.getUserName().equals (dccclient.getUid()))
            {
                if (password.equals (dccclient.getSecretKey()))
                {
                    if (user.getAccountNum().equals (dccclient.getDeviceId()))
                    {
                        if (email.equals (dccclient.getEmail()))
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
        User dccUser = new User();

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
                String temppga = params.getCfg().getPgaClientKey();
                String tempdcc = params.getCfg().getClientKey();

                dccUser.getId().setUid ("portaluser");
                dccUser.getId().setDeviceid (client.getDeviceId());

                dccUser = (User) apiUserSvc.find (dccUser);

                if (dccUser == null)
                {
                    dccUser = new User();

                    dccUser.getId().setUid ("portaltest");
                    dccUser.getId().setDeviceid (client.getDeviceId());

                    dccUser = (User) apiUserSvc.find (dccUser);
                }
                // else do nothing

                // Check for customer key
                if ((client.getDeviceId().equals (temppga))
                 || (client.getDeviceId().equals (tempdcc)))
                {
                    User glbUser = new User();
                    glbUser.getId().setIdUser (dccUser.getId().getIdUser());
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