/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IBatchStatistic;

/**
 *
 * @author Paul.Allen
 */
public class Batch
{
    private String batchId = "0";
    private Date settlementTimeUTC = Calendar.getInstance().getTime();
    private Date settlementLocalTime = Calendar.getInstance().getTime();
    private String settlementState = "unknown";
    private String paymentMethod = "unknown";
    private String marketType = "unknown";
    private String cardPresent = "Card Not Present";

    @XmlAnyElement
    private List<IBatchStatistic> statsList = new ArrayList<IBatchStatistic>();

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
     * @return the settlementTimeUTC
     */
    public Date getSettlementTimeUTC()
    {
        return settlementTimeUTC;
    }

    /**
     * @param settlementTimeUTC the settlementTimeUTC to set
     */
    public void setSettlementTimeUTC (Date settlementTimeUTC)
    {
        this.settlementTimeUTC = settlementTimeUTC;
    }

    /**
     * @return the settlementLocalTime
     */
    public Date getSettlementLocalTime()
    {
        return settlementLocalTime;
    }

    /**
     * @param settlementLocalTime the settlementLocalTime to set
     */
    public void setSettlementLocalTime (Date settlementLocalTime)
    {
        this.settlementLocalTime = settlementLocalTime;
    }

    /**
     * @return the settlementState
     */
    public String getSettlementState()
    {
        return settlementState;
    }

    /**
     * @param settlementState the settlementState to set
     */
    public void setSettlementState (String settlementState)
    {
        this.settlementState = settlementState;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod (String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the marketType
     */
    public String getMarketType()
    {
        return marketType;
    }

    /**
     * @param marketType the marketType to set
     */
    public void setMarketType (String marketType)
    {
        this.marketType = marketType;
    }

    /**
     * @return the cardPresent
     */
    public String getCardPresent()
    {
        return cardPresent;
    }

    /**
     * @param cardPresent the cardPresent to set
     */
    public void setCardPresent (String cardPresent)
    {
        this.cardPresent = cardPresent;
    }

    /**
     * @return the statsList
     */
    public List<IBatchStatistic> getStatsList()
    {
        return statsList;
    }

    /**
     * @param statsList the statsList to set
     */
    public void setStatsList (List<IBatchStatistic> statsList)
    {
        this.statsList = statsList;
    }
}
