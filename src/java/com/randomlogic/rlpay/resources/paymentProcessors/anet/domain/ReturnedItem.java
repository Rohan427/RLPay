/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.util.Date;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IReturnedItem;

/**
 *
 * @author Paul.Allen
 */
public class ReturnedItem implements IReturnedItem
{
    private String id;
    private Date dateUTC;
    private Date dateLocal;
    private String achCode;
    private String desc;

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
     * @return the dateUTC
     */
    @Override
    public Date getDateUTC ()
    {
        return dateUTC;
    }

    /**
     * @param dateUTC the dateUTC to set
     */
    @Override
    public void setDateUTC (Date dateUTC)
    {
        this.dateUTC = dateUTC;
    }

    /**
     * @return the dateLocal
     */
    @Override
    public Date getDateLocal ()
    {
        return dateLocal;
    }

    /**
     * @param dateLocal the dateLocal to set
     */
    @Override
    public void setDateLocal (Date dateLocal)
    {
        this.dateLocal = dateLocal;
    }

    /**
     * @return the achCode
     */
    @Override
    public String getAchCode ()
    {
        return achCode;
    }

    /**
     * @param achCode the achCode to set
     */
    @Override
    public void setAchCode (String achCode)
    {
        this.achCode = achCode;
    }

    /**
     * @return the desc
     */
    @Override
    public String getDesc ()
    {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    @Override
    public void setDesc (String desc)
    {
        this.desc = desc;
    }
}
