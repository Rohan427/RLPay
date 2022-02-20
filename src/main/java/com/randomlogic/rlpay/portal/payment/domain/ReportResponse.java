/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
@XmlType (name="ReportResponse")
public class ReportResponse implements Serializable
{
    private Client client;
    private String errorCode;
    private String errorMessage;
    private String guid;
    private IPayTransactionReport transactionReport;


    public void setClient (Client client)
    {
        this.client = client;
    }

    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }

    public void setErrorMsg (String errorMessage)
    {
        this.setErrorMessage (errorMessage);
    }

    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    @XmlAnyElement
    public void setTransactionReport (IPayTransactionReport transactionReport)
    {
        this.transactionReport = transactionReport;
    }

    /**
     * @return the client
     */
    public Client getClient()
    {
        return client;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode()
    {
        return errorCode;
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
     * @return the guid
     */
    public String getGuid()
    {
        return guid;
    }

    /**
     * @return the transactionReport
     */
    public IPayTransactionReport getTransactionReport()
    {
        return transactionReport;
    }
}
