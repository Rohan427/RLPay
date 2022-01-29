/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Paul.Allen
 */
public interface IDetailedAmount
{

    /**
     * @return the amount
     */
    BigDecimal getAmount ();

    /**
     * @return the description
     */
    String getDescription ();

    /**
     * @return the name
     */
    String getName ();

    /**
     * @param amount the amount to set
     */
    void setAmount (BigDecimal amount);

    /**
     * @param description the description to set
     */
    void setDescription (String description);

    /**
     * @param name the name to set
     */
    void setName (String name);

}
