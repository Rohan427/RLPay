/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface ISubscription
{

    /**
     * @return the id
     */
    int getId ();

    /**
     * @return the payNum
     */
    int getPayNum ();

    /**
     * @param id the id to set
     */
    void setId (int id);

    /**
     * @param payNum the payNum to set
     */
    void setPayNum (int payNum);

}
