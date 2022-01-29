/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.IInternalPaymentProfile;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class InternalPaymentProfile implements IInternalPaymentProfile, Serializable
{
    /** Auth.Net customer type. One of INDIVIDUAL or BUSINESS */
    private int custType = AnetSvcImpl.CUST_INDIVIDUAL;

    /** Billing data for this payment profile*/
    private IBilling billing = null;

    /** Payment data for this payment profile */
    private PaymentInfo payment = null;

    /** Payment profile ID number given by Auth.Net */
    private String profileId = null;

    /** Flag. Default payment profile. true if default */
    private boolean defaultProfile = false;

    public InternalPaymentProfile()
    {

    }

    public InternalPaymentProfile (int custType,
                                   IBilling billing,
                                   PaymentInfo payment
                                  )
    {
        this.custType = custType;
        this.billing = billing;
        this.payment = payment;
    }

    /**
     * @return the defaultProfile
     */
    @Override
    public boolean isDefaultProfile()
    {
        return defaultProfile;
    }

    /**
     * @param defaultProfile the defaultProfile to set
     */
    @Override
    public void setDefaultProfile (boolean defaultProfile)
    {
        this.defaultProfile = defaultProfile;
    }

    /**
     * @return the profileId
     */
    @Override
    public String getProfileId ()
    {
        return profileId;
    }

    /**
     * @param profileId the profileId to set
     */
    @Override
    public void setProfileId (String profileId)
    {
        this.profileId = profileId;
    }

    /**
     * @return the custType
     */
    @Override
    public int getCustType()
    {
        return custType;
    }

    /**
     * @param custType the custType to set
     */
    @Override
    public void setCustType (int custType)
    {
        this.custType = custType;
    }

    /**
     * @return the billing
     */
    @Override
    public IBilling getBilling()
    {
        return billing;
    }

    /**
     * @param billing the billing to set
     */
    @Override
    public void setBilling (IBilling billing)
    {
        this.billing = billing;
    }

    /**
     * @return the payment
     */
    @Override
    public PaymentInfo getPayment()
    {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    @Override
    public void setPayment (PaymentInfo payment)
    {
        this.payment = payment;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof InternalPaymentProfile)
        {
            if (hashCode() == ((InternalPaymentProfile) other).hashCode())
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
        int hash = 3;
        hash = 53 * hash + Objects.hashCode (this.getBilling());
        hash = 53 * hash + Objects.hashCode (this.getCustType());
        hash = 53 * hash + Objects.hashCode (this.getPayment());
        return hash;
    }

    @Override
    public String toString()
    {
        return "Payment Profile: " + hashCode();
    }
}
