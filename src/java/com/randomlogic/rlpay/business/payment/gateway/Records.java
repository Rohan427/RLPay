/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.payment.gateway;

import java.io.Serializable;
import com.randomlogic.rlpay.application.util.ServletParams;
import static com.randomlogic.rlpay.business.interfaces.IPortalManager.ENV_PRODUCTION;
import static com.randomlogic.rlpay.business.interfaces.IPortalManager.ENV_SANDBOX;
import com.randomlogic.rlpay.business.interfaces.IRecords;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.service.impl.AuthenticationSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IAuthenticationSvc;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
public class Records implements IRecords, Serializable
{
    private static Records instance = null;
    private static Object mutex = new Object();
    private static ServletParams params = null;
    private static int environment = IPaymentSvc.ENV_SANDBOX;
    private int mode;

    private Records()
    {
    }

    public static Records getInstance (ServletParams newParams, int newEnv)
    {
        Records result = instance;

        if (instance == null)
        {
            synchronized (mutex)
            {
                result = instance;

                if (result == null)
                {
                    result = new Records();
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
    public IPayTransactionReport getTransactionDetails (TransactionRecord transaction)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        IPayTransactionReport report;

        setAuthCodes (paymentSvc);
        report = paymentSvc.getTransactionDetails (transaction, environment);

        return report;
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

    @Override
    public IPayTransactionReport getTransactionList (TransactionRecord transaction)
    {
        throw new UnsupportedOperationException ("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IPayTransactionReport getUnsettledTransactionList (TransactionRecord transaction)
    {
        throw new UnsupportedOperationException ("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IPayTransactionReport getSettledBatchList (TransactionRecord transaction)
    {
        throw new UnsupportedOperationException ("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IClient verify (IClient client, ServletParams params)
    {
        IAuthenticationSvc dccSvc = new AuthenticationSvcImpl();
        client = dccSvc.verify ((Client)client, params);

         return client;
    }
}
