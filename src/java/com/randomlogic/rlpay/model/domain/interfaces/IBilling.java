/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface IBilling
{
    /**
     * @return the address
     */
    String getAddress();

    /**
     * @return the city
     */
    String getCity();

    /**
     * @return the company
     */
    String getCompany();

    /**
     * @return the country
     */
    String getCountry();

    /**
     * @return the faxNumber
     */
    String getFaxNumber();

    /**
     * @return the firstName
     */
    String getFirstName();

    /**
     * @return the lastName
     */
    String getLastName();

    /**
     * @return the phoneNumber
     */
    String getPhoneNumber();

    /**
     * @return the state
     */
    String getState();

    /**
     * @return the zip
     */
    String getZip();

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
     * @param faxNumber the faxNumber to set
     */
    void setFaxNumber (String faxNumber);

    /**
     * @param firstName the firstName to set
     */
    void setFirstName (String firstName);

    /**
     * @param lastName the lastName to set
     */
    void setLastName (String lastName);

    /**
     * @param phoneNumber the phoneNumber to set
     */
    void setPhoneNumber (String phoneNumber);

    /**
     * @param state the state to set
     */
    void setState (String state);

    /**
     * @param zip the zip to set
     */
    void setZip (String zip);

}
