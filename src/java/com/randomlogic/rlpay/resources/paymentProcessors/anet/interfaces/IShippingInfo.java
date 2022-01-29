/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface IShippingInfo
{

    /**
     * @return the address
     */
    String getAddress ();

    /**
     * @return the city
     */
    String getCity ();

    /**
     * @return the company
     */
    String getCompany ();

    /**
     * @return the country
     */
    String getCountry ();

    /**
     * @return the firstName
     */
    String getFirstName ();

    /**
     * @return the lastName
     */
    String getLastName ();

    /**
     * @return the state
     */
    String getState ();

    /**
     * @param address the address to set
     */
    void setAddress (String address);

    /**
     * @param city the city to set
     */
    void setCity (String city);

    /**
     * @param company the company to set
     */
    void setCompany (String company);

    /**
     * @param country the country to set
     */
    void setCountry (String country);

    /**
     * @param firstName the firstName to set
     */
    void setFirstName (String firstName);

    /**
     * @param lastName the lastName to set
     */
    void setLastName (String lastName);

    /**
     * @param state the state to set
     */
    void setState (String state);

}
