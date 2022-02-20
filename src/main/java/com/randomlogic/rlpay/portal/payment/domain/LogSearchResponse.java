/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.TRANSACTION_SUCCESS;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.TRANS_MSG_SUCCESS;

/**
 *
 * @author Paul.Allen
 */
public class LogSearchResponse implements Serializable
{
    private Collection<Logs> logList = new ArrayList<Logs>();
    private Client client = new Client();
    private String guid;
    private String errorCode = "" + TRANSACTION_SUCCESS;
    private String errorMsg = TRANS_MSG_SUCCESS;

    public LogSearchResponse()
    {

    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorMsg()
    {
        return this.errorMsg;
    }

    public void setErrorMsg (String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public void setLogList (Collection<Logs> logList)
    {
        this.logList = logList;
    }

    public Collection<Logs> getLogList()
    {
        return this.logList;
    }

    public Client getClient()
    {
        return this.client;
    }

    public void setClient (Client client)
    {
        this.client = client;
    }

    public String getGuid()
    {
        return this.guid;
    }

    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    @Override
    public String toString()
    {
        return this.guid + this.client.toString();
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof LogSearchResponse)
        {
            if ((other.hashCode() == this.hashCode())
             && (other.toString().equals (this.toString()))
               )
            {
                result = true;
            }
            // else do nothing
        }
        // else do nothing

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode (this.logList);
        hash = 97 * hash + Objects.hashCode (this.client);
        hash = 97 * hash + Objects.hashCode (this.guid);
        hash = 97 * hash + Objects.hashCode (this.errorCode);
        hash = 97 * hash + Objects.hashCode (this.errorMsg);

        return hash;
    }
}
