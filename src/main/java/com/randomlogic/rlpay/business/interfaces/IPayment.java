/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.interfaces;

import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public interface IPayment extends IPortalManager
{
    ICustomer getPaymentPage (TransactionRecord transaction);

    TransactionRecord processpayment (TransactionRecord transaction);
}
