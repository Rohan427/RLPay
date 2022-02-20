/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class BillingInfo implements IBilling, Serializable
{
    private String firstName;

    private String lastName;

    private String company = "";

    private String address;

    private String city;

    private String state;

    private String zip;

    private String country;

    private String phoneNumber;

    private String faxNumber;

    public BillingInfo()
    {

    }

    public BillingInfo (String firstName, String lastName,
                        String company, String address,
                        String city, String state,
                        String zip, String country,
                        String phoneNumber, String faxNumber
                       )
    {
        this.address = address;
        this.city = city;
        this.company = company;
        this.country = country;
        this.faxNumber = faxNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.zip = zip;
    }

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
     * @return the zip
     */
    @Override
    public String getZip()
    {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    @Override
    public void setZip (String zip)
    {
        this.zip = zip;
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

    /**
     * @return the phoneNumber
     */
    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    @Override
    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the faxNumber
     */
    @Override
    public String getFaxNumber()
    {
        return faxNumber;
    }

    /**
     * @param faxNumber the faxNumber to set
     */
    @Override
    public void setFaxNumber (String faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof BillingInfo)
        {
            if (hashCode() == ((BillingInfo) other).hashCode())
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
        int hash = 8;
        hash = 53 * hash + Objects.hashCode (this.getFirstName ());
        hash = 53 * hash + Objects.hashCode (this.getLastName ());
        hash = 53 * hash + Objects.hashCode (this.getCompany ());
        hash = 53 * hash + Objects.hashCode (this.getAddress ());
        hash = 53 * hash + Objects.hashCode (this.getCity ());
        hash = 53 * hash + Objects.hashCode (this.getState ());
        hash = 53 * hash + Objects.hashCode (this.getZip ());
        hash = 53 * hash + Objects.hashCode (this.getCountry ());
        return hash;
    }

    @Override
    public String toString()
    {
        return "Billing: " + this.getFirstName () + "," + this.getLastName () +
               "," + this.getCompany () + "," + this.getAddress () + "," + this.getCity () +
               "," + this.getState () + "," + this.getZip () + "," + this.getCountry ();
    }
}
