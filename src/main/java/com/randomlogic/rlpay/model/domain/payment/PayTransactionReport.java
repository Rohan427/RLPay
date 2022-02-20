/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ITransactionDetail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class PayTransactionReport implements IPayTransactionReport, Serializable
{
    private Collection<ITransactionDetail> transactions = new ArrayList<ITransactionDetail>();

    private Client client = null;

    private String refId = "0";

    /** Payment processor response code */
    private String responseCode;

    /** Payment processor message */
    private String messageCode;

    /** Payment processor description */
    private String description;

    private int errorCode;

    private String errorMessage;

    @Override
    @XmlAnyElement
    public Collection<ITransactionDetail> getTransactions()
    {
        return this.transactions;
    }

    @Override
    public void setTransactions (Collection<ITransactionDetail> transactions)
    {
        this.transactions = transactions;
    }

    /**
     * @return the client
     */
    @Override
    public Client getClient()
    {
        return client;
    }

    /**
     * @param client the client to set
     */
    @Override
    public void setClient (Client client)
    {
        this.client = client;
    }

    /**
     * @return the refId
     */
    @Override
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    @Override
    public void setRefId (String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the responseCode
     */
    @Override
    public String getResponseCode()
    {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    @Override
    public void setResponseCode (String responseCode)
    {
        this.responseCode = responseCode;
    }

    /**
     * @return the messageCode
     */
    @Override
    public String getMessageCode()
    {
        return messageCode;
    }

    /**
     * @param messageCode the messageCode to set
     */
    @Override
    public void setMessageCode (String messageCode)
    {
        this.messageCode = messageCode;
    }

    /**
     * @return the description
     */
    @Override
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    @Override
    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     * @return the errorCode
     */
    @Override
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    @Override
    public void setErrorCode (int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */
    @Override
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    @Override
    public void setErrorMessage (String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}