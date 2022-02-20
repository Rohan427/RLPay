/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import com.randomlogic.rlpay.model.domain.payment.PaymentInfo;

/**
 *
 * @author Paul.Allen
 */
public interface IInternalPaymentProfile
{
    /**
     * @return the billing
     */
    IBilling getBilling();

    /**
     * @return the custType
     */
    int getCustType();

    /**
     * @return the payment
     */
    PaymentInfo getPayment();

    /**
     * @return the profileId
     */
    String getProfileId();

    /**
     * @return the defaultProfile
     */
    boolean isDefaultProfile();

    /**
     * @param billing the billing to set
     */
    void setBilling (IBilling billing);

    /**
     * @param custType the custType to set
     */
    void setCustType (int custType);

    /**
     * @param defaultProfile the defaultProfile to set
     */
    void setDefaultProfile (boolean defaultProfile);

    /**
     * @param payment the payment to set
     */
    void setPayment (PaymentInfo payment);

    /**
     * @param profileId the profileId to set
     */
    void setProfileId (String profileId);
}
