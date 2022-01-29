/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Paul.Allen
 */
public class BankAccount implements Serializable
{
    private String accountType = "INVALID"; // Either checking, savings, or businessChecking.

    private String routingNumber = "0";

    private String accountNumber = "0";

    private String nameOnAccount = "0";

    private String echeckType = "None"; // Do not use TEL, ARC, or BOC for recurring charges. Either PPD, WEB, or CCD.

    private String bankName = "None";

    // opaqueData

    /* Use COMMON.ACCEPT.INAPP.PAYMENT for Accept transactions.
       For Apple Pay, use COMMON.APPLE.INAPP.PAYMENT.
       For Android Pay, use COMMON.ANDROID.INAPP.PAYMENT.
    */
    private String dataDescriptor = "None";

    private String dataValue = "None";

    public BankAccount()
    {

    }

    /**
     * @return the accountType
     */
    public String getAccountType()
    {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType (String accountType)
    {
        this.accountType = accountType;
    }

    /**
     * @return the routingNumber
     */
    public String getRoutingNumber()
    {
        return routingNumber;
    }

    /**
     * @param routingNumber the routingNumber to set
     */
    public void setRoutingNumber (String routingNumber)
    {
        this.routingNumber = routingNumber;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber()
    {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber (String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the nameOnAccount
     */
    public String getNameOnAccount()
    {
        return nameOnAccount;
    }

    /**
     * @param nameOnAccount the nameOnAccount to set
     */
    public void setNameOnAccount (String nameOnAccount)
    {
        this.nameOnAccount = nameOnAccount;
    }

    /**
     * @return the echeckType
     */
    public String getEcheckType()
    {
        return echeckType;
    }

    /**
     * @param echeckType the echeckType to set
     */
    public void setEcheckType (String echeckType)
    {
        this.echeckType = echeckType;
    }

    /**
     * @return the bankName
     */
    public String getBankName()
    {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName (String bankName)
    {
        this.bankName = bankName;
    }

    /**
     * @return the dataDescriptor
     */
    public String getDataDescriptor()
    {
        return dataDescriptor;
    }

    /**
     * @param dataDescriptor the dataDescriptor to set
     */
    public void setDataDescriptor (String dataDescriptor)
    {
        this.dataDescriptor = dataDescriptor;
    }

    /**
     * @return the dataValue
     */
    public String getDataValue()
    {
        return dataValue;
    }

    /**
     * @param dataValue the dataValue to set
     */
    public void setDataValue (String dataValue)
    {
        this.dataValue = dataValue;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof BankAccount)
        {
            if (hashCode() == ((BankAccount) other).hashCode())
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
        int hash = 4;
        hash = 53 * hash + Objects.hashCode (this.getAccountType ());
        hash = 53 * hash + Objects.hashCode (this.getRoutingNumber ());
        hash = 53 * hash + Objects.hashCode (this.getNameOnAccount ());
        hash = 53 * hash + Objects.hashCode (this.getBankName ());
        return hash;
    }

    @Override
    public String toString()
    {
        return "Payment Type:" + hashCode();
    }
}
