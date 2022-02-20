/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;

/**
 *
 * @author Paul.Allen
 */
public class PaymentRequest implements Serializable
{
    private ClientRequest clientData;

    private AccountData account;

    private ApiNonce paymentNonce;

    public ApiNonce getPaymentNonce()
    {
        return this.paymentNonce;
    }

    public void setPaymentNonce (ApiNonce paymentNonce)
    {
        this.paymentNonce = paymentNonce;
    }

    /**
     * @return the account
     */
    public AccountData getAccount()
    {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount (AccountData account)
    {
        this.account = account;
    }

    /**
     * @return the clientData
     */
    public ClientRequest getClientData()
    {
        return clientData;
    }

    /**
     * @param clientData the clientData to set
     */
    public void setClientData (ClientRequest clientData)
    {
        this.clientData = clientData;
    }
}
