/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.io.Serializable;

/**
 *
 * @author Paul.Allen
 */
public interface IClient extends Serializable
{
    /**
     * @return the deviceId
     */
    String getDeviceId();

    /**
     * @return the AuthToken
     */
    String getAuthToken();

    /**
     * @return the clientUID
     */
    String getClientUID();

    /**
     * @return the email
     */
    String getEmail();

    /**
     *
     * @return password
     */
    String getPassword();

    /**
     * @param accountNum the accountNum to set
     */
    void setDeviceId (String accountNum);

    /**
     * @param AuthToken the AuthToken to set
     */
    void setAuthToken (String AuthToken);

    /**
     * @param clientUID the clientUID to set
     */
    void setClientUID (String clientUID);

    /**
     * @param email the email to set
     */
    void setEmail (String email);

    /**
     *
     * @param password
     */
    void setPassword (String password);
}
