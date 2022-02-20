/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Paul.Allen
 */
public class AccountData implements Serializable
{
    private String accountNo;

    private String statementNo;

    private Collection<LineItem> items;

    private String amount;

    /**
     * @return the accountNo
     */
    public String getAccountNo()
    {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo (String accountNo)
    {
        this.accountNo = accountNo;
    }

    /**
     * @return the statementNo
     */
    public String getStatementNo()
    {
        return statementNo;
    }

    /**
     * @param statementNo the statementNo to set
     */
    public void setStatementNo (String statementNo)
    {
        this.statementNo = statementNo;
    }

    /**
     * @return the items
     */
    public Collection<LineItem> getItems()
    {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems (Collection<LineItem> items)
    {
        this.items = items;
    }

    /**
     * @return the amount
     */
    public String getAmount()
    {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount (String amount)
    {
        this.amount = amount;
    }
}
