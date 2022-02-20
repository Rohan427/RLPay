/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface ICreditCard
{
    /**
     * @return the cardCode
     */
    String getCardCode ();

    /**
     * @return the cardNumber
     */
    String getCardNumber ();

    /**
     * @return the cardType
     */
    String getCardType ();

    /**
     * @return the expirationDate
     */
    String getExpirationDate ();

    /**
     * @param cardCode the cardCode to set
     */
    void setCardCode (String cardCode);

    /**
     * @param cardNumber the cardNumber to set
     */
    void setCardNumber (String cardNumber);

    /**
     * @param cardType the cardType to set
     */
    void setCardType (String cardType);

    /**
     * @param expirationDate the expirationDate to set
     */
    void setExpirationDate (String expirationDate);

}
