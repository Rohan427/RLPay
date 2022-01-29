/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;

/**
 *
 * @author Paul.Allen
 */
public class TransactionRecord implements Serializable
{
    private String command;

    private String mode;

    private TransRequest request;

    private TransResponse response;

    private String refId;

    public TransactionRecord()
    {
        request = new TransRequest();
        response = new TransResponse();
    }

    /**
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId (String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the command
     */
    public String getCommand()
    {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand (String command)
    {
        this.command = command;
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
     * @return the request
     */
    public TransRequest getRequest()
    {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest (TransRequest request)
    {
        this.request = request;
    }

    /**
     * @return the response
     */
    public TransResponse getResponse()
    {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse (TransResponse response)
    {
        this.response = response;
    }
}
