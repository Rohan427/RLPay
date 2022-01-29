/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public class PaymentParms implements Serializable
{
    private ICustomer customer;
    private MerchantAuthenticationType environment;
    private TransactionRecord transaction;
    private ProfileParms profileParms = new ProfileParms();

    /**
     * @return the customer
     */
    public ICustomer getCustomer()
    {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer (ICustomer customer)
    {
        this.customer = customer;
    }

    /**
     * @return the environment
     */
    public MerchantAuthenticationType getEnvironment()
    {
        return environment;
    }

    /**
     * @param environment the environment to set
     */
    public void setEnvironment (MerchantAuthenticationType environment)
    {
        this.environment = environment;
    }

    /**
     * @return the transaction
     */
    public TransactionRecord getTransaction()
    {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction (TransactionRecord transaction)
    {
        this.transaction = transaction;
    }

    /**
     * @return the profileParms
     */
    public ProfileParms getProfileParms()
    {
        return profileParms;
    }

    /**
     * @param profileParms the profileParms to set
     */
    public void setProfileParms (ProfileParms profileParms)
    {
        this.profileParms = profileParms;
    }
}
