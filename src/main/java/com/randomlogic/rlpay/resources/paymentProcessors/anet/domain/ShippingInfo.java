/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IShippingInfo;

/**
 *
 * @author Paul.Allen
 */
public class ShippingInfo implements IShippingInfo
{
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String city;
    private String state;
    private String country;

    /**
     * @return the firstName
     */
    @Override
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    @Override
    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @Override
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    @Override
    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the company
     */
    @Override
    public String getCompany()
    {
        return company;
    }

    /**
     * @param company the company to set
     */
    @Override
    public void setCompany (String company)
    {
        this.company = company;
    }

    /**
     * @return the address
     */
    @Override
    public String getAddress()
    {
        return address;
    }

    /**
     * @param address the address to set
     */
    @Override
    public void setAddress (String address)
    {
        this.address = address;
    }

    /**
     * @return the city
     */
    @Override
    public String getCity()
    {
        return city;
    }

    /**
     * @param city the city to set
     */
    @Override
    public void setCity (String city)
    {
        this.city = city;
    }

    /**
     * @return the state
     */
    @Override
    public String getState()
    {
        return state;
    }

    /**
     * @param state the state to set
     */
    @Override
    public void setState (String state)
    {
        this.state = state;
    }

    /**
     * @return the country
     */
    @Override
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country the country to set
     */
    @Override
    public void setCountry (String country)
    {
        this.country = country;
    }
}
