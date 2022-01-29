/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.parms;

/**
 *
 * @author Paul.Allen
 */
public class WebTransactionDetails
{
    private String firstName = "";
    private String lastName = "";
    private String CustomerId = "";
    private String authAmount = "";
    private String authCode = "";
    private String submitTime = "";
    private String responseCode = "";
    private String responseDesc = "";

    /**
     * @return the CustomerId
     */
    public String getCustomerId()
    {
        return CustomerId;
    }

    /**
     * @param CustomerId the CustomerId to set
     */
    public void setCustomerId (String CustomerId)
    {
        this.CustomerId = CustomerId;
    }

    /**
     * @return the authAmount
     */
    public String getAuthAmount()
    {
        return authAmount;
    }

    /**
     * @param authAmount the authAmount to set
     */
    public void setAuthAmount (String authAmount)
    {
        this.authAmount = authAmount;
    }

    /**
     * @return the submitTime
     */
    public String getSubmitTime()
    {
        return submitTime;
    }

    /**
     * @param submitTime the submitTime to set
     */
    public void setSubmitTime (String submitTime)
    {
        this.submitTime = submitTime;
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode()
    {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode (String responseCode)
    {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseDesc
     */
    public String getResponseDesc()
    {
        return responseDesc;
    }

    /**
     * @param responseDesc the responseDesc to set
     */
    public void setResponseDesc (String responseDesc)
    {
        this.responseDesc = responseDesc;
    }

    /**
     * @return the firstName
     */
    public String getFirstName ()
    {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName ()
    {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return the authCode
     */
    public String getAuthCode ()
    {
        return authCode;
    }

    /**
     * @param authCode the authCode to set
     */
    public void setAuthCode (String authCode)
    {
        this.authCode = authCode;
    }
}
