/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface IFraudFilter
{
    String getAction();

    String getName();

    void setAction (String action);

    void setName (String name);
}
