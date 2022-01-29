/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;

/**
 *
 * @author Paul.Allen
 */
public class ConfigBean implements Serializable
{
    private static final long serialVersionUID = 8648388624506174894L;

    private String mode = "test";

    private String apiKey = "none";

    private String transactionId = "none";

    private String  clientKey = "none";

    public ConfigBean()
    {
        super();
    }

    /**
     * @return the mode
     */
    public String getMode()
    {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode (String mode)
    {
        this.mode = mode;
    }

    /**
     * @return the apiKey
     */
    public String getApiKey()
    {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey (String apiKey)
    {
        this.apiKey = apiKey;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId()
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
     * @return the clientKey
     */
    public String getClientKey()
    {
        return clientKey;
    }

    /**
     * @param clientKey the clientKey to set
     */
    public void setClientKey (String clientKey)
    {
        this.clientKey = clientKey;
    }
}
