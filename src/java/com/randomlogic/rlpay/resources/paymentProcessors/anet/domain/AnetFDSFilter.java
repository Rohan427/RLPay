/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IFraudFilter;

/**
 *
 * @author Paul.Allen
 */
public class AnetFDSFilter implements IFraudFilter
{
    protected String name;
    protected String action;

    public AnetFDSFilter()
    {
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String getAction()
    {
        return this.action;
    }

    @Override
    public void setAction (String action)
    {
        this.action = action;
    }
}
