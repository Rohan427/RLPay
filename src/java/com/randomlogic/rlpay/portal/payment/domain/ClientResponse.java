/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.payment.Client;

/**
 *
 * @author Paul.Allen
 */
public class ClientResponse implements Serializable
{
    /** Implementor Customer ID used as MerchantCustomerID for Auth.Net */
    private String custAccount = "0";

    /** Auth.Net Customer ID for referencing Auth.Net Customer profile */
    private String profileToken = "0";

    /** The user's login ID */
    private String userName = null;

    /** The user's email address */
    private String email = null;

    /** The link to the hosted page being requested */
    private String hostedUrl = null;

    /** The required token needed to access the hostedUrl */
    private String hostedToken;

    /** Used for transaction tracking to correlate between the client and the
     * providers.
     */
    private String guid;

    private String errorMsg;

    private String errorCode;

    private Client client;

    private String transactionId;

    private String authCode;

    public Client getClient()
    {
        return this.client;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId ()
    {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId (String transactionId)
    {
        this.transactionId = transactionId;
    }

    /**
     * @return the authCode
     */
    public String getAuthCode ()
    {
        return authCode;
    }

    /**
     * @param authCode the authCode to set
     */
    public void setAuthCode (String authCode)
    {
        this.authCode = authCode;
    }

    public void setClient (Client client)
    {
        this.client = client;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg ()
    {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg (String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode ()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * @return the guid
     */
    public String getGuid()
    {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    /**
     * @return the custAccount
     */
    public String getCustAccount()
    {
        return custAccount;
    }

    /**
     * @param custAccount the custAccount to set
     */
    public void setCustAccount (String custAccount)
    {
        this.custAccount = custAccount;
    }

    /**
     * @return the profileToken
     */
    public String getProfileToken()
    {
        return profileToken;
    }

    /**
     * @param profileToken the profileToken to set
     */
    public void setProfileToken (String profileToken)
    {
        this.profileToken = profileToken;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName (String userName)
    {
        this.userName = userName;
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
     * @return the hostedUrl
     */
    public String getHostedUrl()
    {
        return hostedUrl;
    }

    /**
     * @param hostedUrl the hostedUrl to set
     */
    public void setHostedUrl (String hostedUrl)
    {
        this.hostedUrl = hostedUrl;
    }

    /**
     * @return the hostedToken
     */
    public String getHostedToken()
    {
        return hostedToken;
    }

    /**
     * @param hostedToken the hostedToken to set
     */
    public void setHostedToken (String hostedToken)
    {
        this.hostedToken = hostedToken;
    }
}
