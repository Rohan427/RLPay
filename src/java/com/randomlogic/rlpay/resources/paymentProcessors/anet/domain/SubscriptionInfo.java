/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ISubscription;

/**
 *
 * @author Paul.Allen
 */
public class SubscriptionInfo implements ISubscription
{
    private int id;
    private int payNum;

    /**
     * @return the id
     */
    @Override
    public int getId ()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId (int id)
    {
        this.id = id;
    }

    /**
     * @return the payNum
     */
    @Override
    public int getPayNum ()
    {
        return payNum;
    }

    /**
     * @param payNum the payNum to set
     */
    @Override
    public void setPayNum (int payNum)
    {
        this.payNum = payNum;
    }
}
