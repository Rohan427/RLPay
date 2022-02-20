/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.BankAccount;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Paul.Allen
 */
public class PaymentInfo implements Serializable
{
    /** Customer credit card data */
    private ICreditCard creditcard =  null;

    /** Customer bank account data*/
    private BankAccount bankaccount = null;

    /**
     * @return the creditcard
     */
    public ICreditCard getCreditcard()
    {
        return creditcard;
    }

    /**
     * @param creditcard the creditcard to set
     */
    public void setCreditcard (ICreditCard creditcard)
    {
        this.creditcard = creditcard;
    }

    /**
     * @return the bankaccount
     */
    public BankAccount getBankaccount()
    {
        return bankaccount;
    }

    /**
     * @param bankaccount the bankaccount to set
     */
    public void setBankaccount (BankAccount bankaccount)
    {
        this.bankaccount = bankaccount;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof PaymentInfo)
        {
            if (hashCode() == ((PaymentInfo) other).hashCode())
            {
                result = true;
            }
            else
            {

            }
        }
        else
        {
            ;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 2;
        hash = 53 * hash + Objects.hashCode (this.getBankaccount());
        hash = 53 * hash + Objects.hashCode (this.getCreditcard());
        return hash;
    }

    @Override
    public String toString()
    {
        return "Payment Method: " + hashCode();
    }
}
