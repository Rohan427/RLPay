/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;

/**
 *
 * @author Paul.Allen
 */
public class TransResponse implements Serializable
{
    /** The type of the transaction */
    private String transactionType;

    /** Payment processor response code */
    private String responseCode;

    /** Payment processor message */
    private String messageCode;

    /** Payment processor description */
    private String description;

    private String authCode;

    private int errorCode;

    private String errorMessage;

    private String transactionId = "0";

    private IPayTransactionReport transactionreport = null;

    /**
     * @return the transactionType
     */
    public String getTransactionType()
    {
        return transactionType;
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
     * @param transactionType the transactionType to set
     */
    public void setTransactionType (String transactionType)
    {
        this.transactionType = transactionType;
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode()
    {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode (String responseCode)
    {
        this.responseCode = responseCode;
    }

    /**
     * @return the messageCode
     */
    public String getMessageCode()
    {
        return messageCode;
    }

    /**
     * @param messageCode the messageCode to set
     */
    public void setMessageCode (String messageCode)
    {
        this.messageCode = messageCode;
    }

    /**
     * @return the description
     */
    public String getDescription()
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
     * @return the authCode
     */
    public String getAuthCode()
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

    /**
     * @return the errorCode
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage (String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the transactionreport
     */
    public IPayTransactionReport getTransactionreport()
    {
        return transactionreport;
    }

    /**
     * @param transactionreport the transactionreport to set
     */
    public void setTransactionreport (IPayTransactionReport transactionreport)
    {
        this.transactionreport = transactionreport;
    }
}
