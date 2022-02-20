/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

/**
 *
 * @author Paul.Allen
 */
public class ApiNonce
{
    private String description = "0";

    private String nonce = "0";

    private String key = "0";

    /**
     * @return the description
     */
    public String getDescription ()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     * @return the nonce
     */
    public String getNonce ()
    {
        return nonce;
    }

    /**
     * @param nonce the nonce to set
     */
    public void setNonce (String nonce)
    {
        this.nonce = nonce;
    }

    /**
     * @return the key
     */
    public String getKey ()
    {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey (String key)
    {
        this.key = key;
    }
}
