/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import com.randomlogic.rlpay.model.domain.payment.Client;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.CMD_NONE;

/**
 *
 * @author Paul.Allen
 */
public class ReportRequest implements Serializable
{
    private String guid = null;
    private Client client = new Client();
    private String command = CMD_NONE;
    private String beginDate;
    private String endDate;
    private String transactionId;
    private String batchId;
    private String orderBy;
    private boolean descending;
    private int limit;
    private int offset;
    private String customerId;
    private Collection<String> profileIdList = new ArrayList<String>();

    public String getGuid()
    {
        return this.guid;
    }

    public String getCommand()
    {
        return this.command;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    /**
     * @return the client
     */
    public Client getClient()
    {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient (Client client)
    {
        this.client = client;
    }

    /**
     * @param command the command to set
     */
    public void setCommand (String command)
    {
        this.command = command;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate (String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate (String endDate)
    {
        this.endDate = endDate;
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
     * @return the batchId
     */
    public String getBatchId()
    {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId (String batchId)
    {
        this.batchId = batchId;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy()
    {
        return orderBy;
    }

    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy (String orderBy)
    {
        this.orderBy = orderBy;
    }

    /**
     * @return the descending
     */
    public boolean isDescending()
    {
        return descending;
    }

    /**
     * @param descending the descending to set
     */
    public void setDescending (boolean descending)
    {
        this.descending = descending;
    }

    /**
     * @return the limit
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit (int limit)
    {
        this.limit = limit;
    }

    /**
     * @return the offset
     */
    public int getOffset()
    {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset (int offset)
    {
        this.offset = offset;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the profileIdList
     */
    public Collection<String> getProfileIdList()
    {
        return profileIdList;
    }

    /**
     * @param profileIdList the profileIdList to set
     */
    public void setProfileIdList (Collection<String> profileIdList)
    {
        this.profileIdList = profileIdList;
    }
}
