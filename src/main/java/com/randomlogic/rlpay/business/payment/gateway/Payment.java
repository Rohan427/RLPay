/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.payment.gateway;

import java.io.Serializable;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.business.interfaces.IPayment;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
public class Payment implements IPayment, Serializable
{
    private static Payment instance = null;
    private static final Object mutex = new Object();
    private static ServletParams params = null;
    private static int environment = ENV_SANDBOX;
    private int mode;

    private Payment()
    {
    }

    public static Payment getInstance (ServletParams newParams, int newEnv)
    {
        Payment result = instance;

        if (instance == null)
        {
            synchronized (mutex)
            {
                result = instance;

                if (result == null)
                {
                    result = new Payment();
                }
                else
                {
                    ;// Do nothing, use existing
                }

                params = newParams;
                environment = newEnv;
            }
        }
        else
        {
            ;// Do nothing
        }

        return result;
    }

    @Override
    public void initManager (ServletParams newParams, int newEnv)
    {
        params = newParams;
        environment = newEnv;
    }

    @Override
    public ICustomer getPaymentPage (TransactionRecord transaction)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();

        setAuthCodes (paymentSvc);
        transaction = paymentSvc.getHostedPaymentPageRequest (transaction, environment);

        return transaction.getRequest().getCustomer();
    }

    @Override
    public TransactionRecord processpayment (TransactionRecord transaction)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();

        setAuthCodes (paymentSvc);
        transaction = paymentSvc.executePaymentRequest (transaction, environment);

        return transaction;
    }

    private void setAuthCodes (IPaymentSvc paymentSvc)
    {
        if (environment == ENV_SANDBOX)
        {
            paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
            paymentSvc.setTransactionKey (params.getCfg().getTestTransId());
        }
        else if (environment == ENV_PRODUCTION)
        {
            paymentSvc.setApiLoginId (params.getCfg().getLiveApiLoginId());
            paymentSvc.setTransactionKey (params.getCfg().getLiveTransId());
        }
    }
}
