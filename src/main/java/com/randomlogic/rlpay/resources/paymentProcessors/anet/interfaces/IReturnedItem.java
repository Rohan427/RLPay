/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

import java.util.Date;

/**
 *
 * @author Paul.Allen
 */
public interface IReturnedItem
{
    /**
     * @return the achCode
     */
    String getAchCode ();

    /**
     * @return the dateLocal
     */
    Date getDateLocal ();

    /**
     * @return the dateUTC
     */
    Date getDateUTC ();

    /**
     * @return the desc
     */
    String getDesc ();

    /**
     * @return the id
     */
    String getId ();

    /**
     * @param achCode the achCode to set
     */
    void setAchCode (String achCode);

    /**
     * @param dateLocal the dateLocal to set
     */
    void setDateLocal (Date dateLocal);

    /**
     * @param dateUTC the dateUTC to set
     */
    void setDateUTC (Date dateUTC);

    /**
     * @param desc the desc to set
     */
    void setDesc (String desc);

    /**
     * @param id the id to set
     */
    void setId (String id);

}
