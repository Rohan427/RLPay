/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paul.Allen
 */
public interface ICustomer extends Serializable
{
    boolean addPaymentProfile (IInternalPaymentProfile profile);

    Object getErrorResponse();

    String getCustomerId();

    IInternalPaymentProfile getDefaultPaymentProfile();

    boolean setDefaultPaymentProfile (IInternalPaymentProfile profile);

    String getDescription();

    String getEmail();

    Collection<IInternalPaymentProfile> getPaymentProfiles();

    String getProfileToken();

    /**
     * @return the profileType
     */
    int getProfileType();

    String getRefId();

    /**
     * @return the token
     */
    String getToken();

    boolean removePaymentProfile (IInternalPaymentProfile profile);

    void setErrorReponse (Object anetResponse);

    void setCustomerId (String customerId);

    void setDescription (String description);

    void setEmail (String email);

    void setPaymentProfiles (Collection<IInternalPaymentProfile> paymentProfiles);

    void setProfileToken (String profileToken);

    /**
     * @param profileType the profileType to set
     */
    void setProfileType (int profileType);

    void setRefId (String refId);

    /**
     * @param token the token to set
     */
    void setToken (String token);
}
