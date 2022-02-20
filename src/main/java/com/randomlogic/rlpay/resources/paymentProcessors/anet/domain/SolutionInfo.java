/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ISolutionInfo;

/**
 *
 * @author Paul.Allen
 */
public class SolutionInfo implements ISolutionInfo
{
    private String id;
    private String name;
    private String vendorName;

    /**
     * @return the id
     */
    @Override
    public String getId ()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId (String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    @Override
    public String getName ()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * @return the vendorName
     */
    @Override
    public String getVendorName ()
    {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    @Override
    public void setVendorName (String vendorName)
    {
        this.vendorName = vendorName;
    }
}
