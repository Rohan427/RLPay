/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.interfaces.IAccount;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.interfaces.IInvoice;

/**
 *
 * @author Paul.Allen
 */
public class Account implements IAccount, Serializable
{
    private String accountNo;
    private Date statementDate;
    private String statementNo;
    private String balanceDue;
    private String endingBalance;
    private Collection<IInvoice> invoices;// List of invoices to be paid or displayed

    public Account()
    {

    }

    /**
     * @return the invoices
     */
    @Override
    public Collection<IInvoice> getInvoices()
    {
        return invoices;
    }

    /**
     * @param invoices the invoices to set
     */
    @Override
    public void setInvoices (Collection<IInvoice> invoices)
    {
        this.invoices = invoices;
    }

    /**
     * @return the accountNo
     */
    @Override
    public String getAccountNo()
    {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    @Override
    public void setAccountNo (String accountNo)
    {
        this.accountNo = accountNo;
    }

    /**
     * @return the statementDate
     */
    @Override
    public Date getStatementDate()
    {
        return statementDate;
    }

    /**
     * @param statementDate the statementDate to set
     */
    @Override
    public void setStatementDate (Date statementDate)
    {
        this.statementDate = statementDate;
    }

    /**
     * @return the statementNo
     */
    @Override
    public String getStatementNo()
    {
        return statementNo;
    }

    /**
     * @param statementNo the statementNo to set
     */
    @Override
    public void setStatementNo (String statementNo)
    {
        this.statementNo = statementNo;
    }

    /**
     * @return the balanceDue
     */
    @Override
    public String getBalanceDue()
    {
        return balanceDue;
    }

    /**
     * @param balanceDue the balanceDue to set
     */
    @Override
    public void setBalanceDue (String balanceDue)
    {
        this.balanceDue = balanceDue;
    }

    /**
     * @return the endingBalance
     */
    @Override
    public String getEndingBalance()
    {
        return endingBalance;
    }

    /**
     * @param endingBalance the endingBalance to set
     */
    @Override
    public void setEndingBalance (String endingBalance)
    {
        this.endingBalance = endingBalance;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof Account)
        {
            if (hashCode() == ((Account) other).hashCode())
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
        int hash = 2;
        hash = 53 * hash + Objects.hashCode (this.accountNo);
        hash = 53 * hash + Objects.hashCode (this.statementNo);
        return hash;
    }

    @Override
    public String toString()
    {
        return "Account: " + this.accountNo + ", " + this.statementNo;
    }
}
