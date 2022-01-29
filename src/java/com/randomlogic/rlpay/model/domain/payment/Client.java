/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;

/**
 *
 * @author Paul.Allen
 */
public class Client implements Serializable, IClient
{
    /** Client Customer ID used as MerchantCustomerID for Auth.Net */
    private String deviceId = "0";

    /**
     * Token used once client has been authenticated.
     * When 0, client is not authenticated
     */
    private String authToken = "0";

    /** The client's login ID */
    private String clientUID = null;

    /** The user's email address */
    private String email = null;

    /**
     * Used for login only, DO NOT store in system after authentications
     */
    private String password;

    /**
     *
     * @return
     */
    @Override
    public String getPassword()
    {
        return this.password;
    }

    /**
     *
     * @param password
     */
    @Override
    public void setPassword (String password)
    {
        this.password = password;
    }

    /**
     * @return the clientUID
     */
    @Override
    public String getClientUID()
    {
        return clientUID;
    }

    /**
     * @param clientUID the clientUID to set
     */
    @Override
    public void setClientUID (String clientUID)
    {
        this.clientUID = clientUID;
    }

    /**
     * @return the deviceId
     */
    @Override
    public String getDeviceId()
    {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    @Override
    public void setDeviceId (String deviceId)
    {
        this.deviceId = deviceId;
    }

    /**
     * @return the authToken
     */
    @Override
    public String getAuthToken()
    {
        return authToken;
    }

    /**
     * @param authToken the authToken to set
     */
    @Override
    public void setAuthToken (String authToken)
    {
        this.authToken = authToken;
    }

    /**
     * @return the email
     */
    @Override
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */

    @Override
    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof Client)
        {
            if (hashCode() == ((Client) other).hashCode())
            {
                result = true;
            }
            else
            {

            }
        }
        else
        {
            ;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode (this.deviceId);
        hash = 53 * hash + Objects.hashCode (this.clientUID);
        hash = 53 * hash + Objects.hashCode (this.email);
        return hash;
    }

    @Override
    public String toString()
    {
        return "User: " + this.deviceId + ", " + this.clientUID + ", " + this.email;
    }
}
