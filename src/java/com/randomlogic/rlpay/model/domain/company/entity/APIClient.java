/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.entity;

import java.io.Serializable;

/**
 *
 * @author Paul.Allen
 */
public class APIClient implements Serializable
{
    private String uid;
    private String secretKey;
    private String deviceId;
    private String email;
    private boolean portalAccess;

    public APIClient (String uid, String deviceId, String password, String email, boolean portalAccess)
    {
        this.uid = uid;
        this.deviceId = deviceId;
        this.secretKey = password;
        this.email = email;
        this.portalAccess = portalAccess;
    }

    public String getUid()
    {
        return this.uid;
    }

    public void setUid (String uid)
    {
        this.uid = uid;
    }

    public String getSecretKey()
    {
        return this.secretKey;
    }

    public void setSecretKey (String secretKey)
    {
        this.secretKey = secretKey;
    }

    public String getDeviceId()
    {
        return this.deviceId;
    }

    public void setDeviceId (String deviceId)
    {
        this.deviceId = deviceId;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail (String email)
    {
        this.email = email;
    }

    /**
     * @return the portalAccess
     */
    public boolean isPortalAccess()
    {
        return portalAccess;
    }

    /**
     * @param portalAccess the portalAccess to set
     */
    public void setPortalAccess (boolean portalAccess)
    {
        this.portalAccess = portalAccess;
    }
}
