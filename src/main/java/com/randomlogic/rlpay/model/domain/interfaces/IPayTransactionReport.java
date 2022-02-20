/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ITransactionDetail;
import java.util.Collection;
import com.randomlogic.rlpay.model.domain.payment.Client;

/**
 *
 * @author Paul.Allen
 */
public interface IPayTransactionReport
{
    Collection<ITransactionDetail> getTransactions();

    void setTransactions (Collection<ITransactionDetail> transactions);

    Client getClient();

    void setClient (Client client);

    String getRefId();

    void setRefId (String refId);

    String getResponseCode();

    void setResponseCode (String responseCode);

    String getMessageCode();

    void setMessageCode (String messageCode);

    String getDescription();

    void setDescription (String description);

    int getErrorCode();

    void setErrorCode (int errorCode);

    String getErrorMessage();

    void setErrorMessage (String errorMessage);
}
