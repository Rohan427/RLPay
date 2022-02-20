/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.io.Serializable;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;

/**
 *
 * @author Paul.Allen
 */
public class CreditCard implements ICreditCard, Serializable
{
    private String cardNumber = "0";

    private String expirationDate = "";  // Use XML gYearMonth (YYYY-MM) formatting.

    private String cardCode = null;

    private String cardType = "None";

    public CreditCard (String cardNumber, String expirationDate,
                       String cardType, String cardCode
                      )
    {
        this.cardCode = cardCode;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardType = cardType;
    }

    public CreditCard()
    {

    }

    /**
     * @return the cardNumber
     */
    @Override
    public String getCardNumber()
    {
        return cardNumber;
    }

        /**
     * @return the cardType
     */
    @Override
    public String getCardType()
    {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    @Override
    public void setCardType (String cardType)
    {
        this.cardType = cardType;
    }

    /**
     * @param cardNumber the cardNumber to set
     */
    @Override
    public void setCardNumber (String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the expirationDate
     */
    @Override
    public String getExpirationDate()
    {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    @Override
    public void setExpirationDate (String expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    /**
     * @return the cardCode
     */
    @Override
    public String getCardCode()
    {
        return cardCode;
    }

    /**
     * @param cardCode the cardCode to set
     */
    @Override
    public void setCardCode (String cardCode)
    {
        this.cardCode = cardCode;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof CreditCard)
        {
            if (hashCode() == ((CreditCard) other).hashCode())
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
        hash = 53 * hash + Objects.hashCode (this.getCardNumber ());
        hash = 53 * hash + Objects.hashCode (this.getExpirationDate ());
        return hash;
    }

    @Override
    public String toString()
    {
        return "Payment Type:" + hashCode();
    }
}
