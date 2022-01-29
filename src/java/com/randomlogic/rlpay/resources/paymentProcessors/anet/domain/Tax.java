/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.math.BigDecimal;
import com.randomlogic.rlpay.model.domain.interfaces.IDetailedAmount;

/**
 *
 * @author Paul.Allen
 */
public class Tax implements IDetailedAmount
{
    private BigDecimal amount = new BigDecimal (0.00);
    private String name = "None";
    private String description = "None";
    private boolean taxExempt = false;

    /**
     * @return the amount
     */
    @Override
    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    @Override
    public void setAmount (BigDecimal amount)
    {
        this.amount = amount;
    }

    /**
     * @return the name
     */
    @Override
    public String getName()
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
     * @return the description
     */
    @Override
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    @Override
    public void setDescription (String description)
    {
        this.description = description;
    }

    public boolean isTaxExempt()
    {
        return this.taxExempt;
    }

    public void setTaxExempt (boolean taxExempt)
    {
        this.taxExempt = taxExempt;
    }
}
