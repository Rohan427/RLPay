/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Paul.Allen
 */
public class PaymentPacket implements Serializable
{
    private String errorMessage;

    private String errorCode;

    private String amount;

    private String transactionCode;

    private Date dateCompleted;

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
     * @return the errorCode
     */
    public String getErrorCode()
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
     * @return the amount
     */
    public String getAmount()
    {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    /**
     * @return the transactionCode
     */
    public String getTransactionCode()
    {
        return transactionCode;
    }

    /**
     * @param transactionCode the transactionCode to set
     */
    public void setTransactionCode (String transactionCode)
    {
        this.transactionCode = transactionCode;
    }

    /**
     * @return the dateCompleted
     */
    public Date getDateCompleted()
    {
        return dateCompleted;
    }

    /**
     * @param dateCompleted the dateCompleted to set
     */
    public void setDateCompleted (Date dateCompleted)
    {
        this.dateCompleted = dateCompleted;
    }
}
