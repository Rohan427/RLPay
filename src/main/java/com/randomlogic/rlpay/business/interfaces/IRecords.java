/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.interfaces;

import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public interface IRecords extends IPortalManager
{
    IPayTransactionReport getTransactionDetails (TransactionRecord transaction);

    IPayTransactionReport getTransactionList (TransactionRecord transaction);

    IPayTransactionReport getUnsettledTransactionList (TransactionRecord transaction);

    IPayTransactionReport getSettledBatchList (TransactionRecord transaction);

    IClient verify (IClient client, ServletParams params);
}
