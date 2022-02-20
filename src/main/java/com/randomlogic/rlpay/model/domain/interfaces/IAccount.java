/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.sql.Date;
import java.util.Collection;

/**
 *
 * @author Paul.Allen
 */
public interface IAccount
{
    public Collection<IInvoice> getInvoices();

    void setInvoices (Collection<IInvoice> invoices);

    /**
     * @return the accountNo
     */
    String getAccountNo();

    /**
     * @return the balanceDue
     */
    String getBalanceDue();

    /**
     * @return the endingBalance
     */
    String getEndingBalance();

    /**
     * @return the statementDate
     */
    Date getStatementDate();

    /**
     * @return the statementNo
     */
    String getStatementNo();

    /**
     * @param accountNo the accountNo to set
     */
    void setAccountNo (String accountNo);

    /**
     * @param balanceDue the balanceDue to set
     */
    void setBalanceDue (String balanceDue);

    /**
     * @param endingBalance the endingBalance to set
     */
    void setEndingBalance (String endingBalance);

    /**
     * @param statementDate the statementDate to set
     */
    void setStatementDate (Date statementDate);

    /**
     * @param statementNo the statementNo to set
     */
    void setStatementNo (String statementNo);
}
