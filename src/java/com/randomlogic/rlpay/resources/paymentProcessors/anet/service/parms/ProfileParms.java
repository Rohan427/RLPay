/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms;

import java.io.Serializable;
import static com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc.NONE_MODE;

/**
 *
 * @author Paul.Allen
 */
public class ProfileParms implements Serializable
{
    private String customerProfileId;
    private String customerId;
    private String email;
    private String refId;
    private boolean unmaskExpirationDate = false;
    private boolean includeIssuerInfo = false;
    private boolean secure = true;
    private int validationMode = NONE_MODE;

    /**
     * @return the customerProfileId
     */
    public String getCustomerProfileId()
    {
        return customerProfileId;
    }

    /**
     * @param customerProfileId the customerProfileId to set
     */
    public void setCustomerProfileId (String customerProfileId)
    {
        this.customerProfileId = customerProfileId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail (String email)
    {
        this.email = email;
    }

    /**
     * @return the unmaskExpirationDate
     */
    public boolean isUnmaskExpirationDate()
    {
        return unmaskExpirationDate;
    }

    /**
     * @param unmaskExpirationDate the unmaskExpirationDate to set
     */
    public void setUnmaskExpirationDate (boolean unmaskExpirationDate)
    {
        this.unmaskExpirationDate = unmaskExpirationDate;
    }

    /**
     * @return the includeIssuerInfo
     */
    public boolean isIncludeIssuerInfo()
    {
        return includeIssuerInfo;
    }

    /**
     * @param includeIssuerInfo the includeIssuerInfo to set
     */
    public void setIncludeIssuerInfo (boolean includeIssuerInfo)
    {
        this.includeIssuerInfo = includeIssuerInfo;
    }

    /**
     * @return the secure
     */
    public boolean isSecure()
    {
        return secure;
    }

    /**
     * @param secure the secure to set
     */
    public void setSecure (boolean secure)
    {
        this.secure = secure;
    }

    /**
     * @return the validationMode
     */
    public int getValidationMode()
    {
        return validationMode;
    }

    /**
     * @param validationMode the validationMode to set
     */
    public void setValidationMode (int validationMode)
    {
        this.validationMode = validationMode;
    }

    /**
     * @return the refId
     */
    public String getRefId ()
    {
        return refId;
    }

    /**
     * @param refId the refId to set
     */
    public void setRefId (String refId)
    {
        this.refId = refId;
    }
}
