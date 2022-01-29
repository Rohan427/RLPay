/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface ISolutionInfo
{
    /**
     * @return the id
     */
    String getId();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the vendorName
     */
    String getVendorName();

    /**
     * @param id the id to set
     */
    void setId (String id);

    /**
     * @param name the name to set
     */
    void setName (String name);

    /**
     * @param vendorName the vendorName to set
     */
    void setVendorName (String vendorName);

}
