/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;

/**
 *
 * @author Paul.Allen
 */
public class LogSearchRequest implements Serializable
{
    private Logs log;
    private Date beginDate;
    private Date endDate;
    private Client client;
    private String guid;

    public LogSearchRequest()
    {

    }

    public String getGuid()
    {
        return this.guid;
    }

    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    public Client getClient()
    {
        return this.client;
    }

    public void setClient (Client client)
    {
        this.client = client;
    }

    /**
     * @return the log
     */
    public Logs getLog()
    {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog (Logs log)
    {
        this.log = log;
    }

    /**
     * @return the beginDate
     */
    public Date getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate (Date beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate (Date endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public String toString()
    {
        return client.toString() + log.toString() + beginDate.toString() + endDate.toString();
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof LogSearchRequest)
        {
            if ((other.toString().equals (this.toString()))
             && (other.hashCode() == this.hashCode())
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
        hash = 83 * hash + Objects.hashCode (this.log);
        hash = 83 * hash + Objects.hashCode (this.beginDate);
        hash = 83 * hash + Objects.hashCode (this.endDate);
        hash = 83 * hash + Objects.hashCode (this.guid);

        return hash;
    }

}
