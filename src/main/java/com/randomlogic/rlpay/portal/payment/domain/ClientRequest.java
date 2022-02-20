/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.randomlogic.rlpay.model.domain.payment.Client;

/**
 *
 * @author Paul.Allen
 */
public class ClientRequest implements Serializable
{
    /**
     * Implementor Customer ID used as MerchantCustomerID for Auth.Net
     */
    private String custAccount = "0";

    /**
     * Auth.Net Customer ID for referencing Auth.Net Customer profile
     */
    private String profileToken = "0";

    /**
     * The customer contact's email address
     */
    private String email = null;

    /**
     * Description of this account.
     */
    private String description;

    /**
     * The link to the hosted page being requested
     */
    private String hostedUrl = null;

    /**
     * Used for transaction tracking to correlate between the client and the
     * providers.
     */
    private String guid;

    /**
     * Client authentication data. Used to authorize the client machine for system
     * use.
     */
    private Client client;

    /**
     * Map of hosted page settings. Settings vary with payment and profile pages.
     */
    private Map<String, String> hostedPageSettings = new HashMap<String, String>();

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public Client getClient()
    {
        return this.client;
    }

    /**
     *
     * @param client
     */
    public void setClient (Client client)
    {
        this.client = client;
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
     * @return the hostedPageSettings
     */
    public Map<String, String> getHostedPageSettings()
    {
        return hostedPageSettings;
    }

    /**
     * @param hostedPageSettings the hostedPageSettings to set
     */
    public void setHostedPageSettings (Map<String, String> hostedPageSettings)
    {
        this.hostedPageSettings = hostedPageSettings;
    }
}
