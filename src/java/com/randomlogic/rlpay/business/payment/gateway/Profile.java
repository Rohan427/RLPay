/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.payment.gateway;

import java.io.Serializable;
import java.util.Map;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.business.interfaces.IProfile;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.service.impl.AuthenticationSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import static com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc.NONE_MODE;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;
import org.springframework.stereotype.Component;
import com.randomlogic.rlpay.model.service.interfaces.IAuthenticationSvc;

/**
 *
 * @author Paul.Allen
 */
@Component
public class Profile implements IProfile, Serializable
{
    private static Profile instance = null;
    private static Object mutex = new Object();
    private static ServletParams params = null;

    private static int environment = IPaymentSvc.ENV_SANDBOX;
    private int mode = NONE_MODE;

    private Profile()
    {
    }

    /**
     *
     * @param newParams
     * @param newEnv
     * @return
     */
    public static Profile getInstance (ServletParams newParams, int newEnv)
    {
        Profile result = instance;

        if (instance == null)
        {
            synchronized (mutex)
            {
                result = instance;

                if (result == null)
                {
                    result = new Profile();
                }
                // else Do nothing

                params = newParams;
                environment = newEnv;
            }
        }
        // else Do nothing

        return result;
    }

    @Override
    public void initManager (ServletParams newParams, int newEnv)
    {
        params = newParams;
        environment = newEnv;
    }

    /**
     *
     * @param password
     * @param UID
     * @param deviceId
     * @return
     */
    @Override
    public IClient login (String password, String UID, String deviceId)
    {
        IClient client = new Client();
        IAuthenticationSvc dccSvc = new AuthenticationSvcImpl();

        client.setClientUID (UID);
        client.setDeviceId (deviceId);
        client = dccSvc.login ((Client)client, password);

        return client;
    }

    /**
     *
     * @param customer
     * @param refId
     * @return
     */
    @Override
    public ICustomer register (ICustomer customer, String refId)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();

        //Customer
        customer = paymentSvc.createCustomer (customer.getCustomerId(),
                                              customer.getDescription(),
                                              customer.getEmail(),
                                              customer.getProfileType(),
                                              refId
                                             );

        //Create the profile
        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

        customer = paymentSvc.createCustomerProfile (customer,
                                                     mode,
                                                     Profile.environment
                                                    );

        return customer;
    }

    /**
     *
     * @param client
     * @param params
     * @return
     */
    @Override
    public IClient verify (IClient client, ServletParams params)
    {
        IAuthenticationSvc dccSvc = new AuthenticationSvcImpl();
        client = dccSvc.verify ((Client)client, params);
        return client;
    }

    /**
     *
     * @param customer the value of user
     * @param environment the value of environment
     * @param refId the value of refId
     * @return the model.domain.interfaces.ICustomer
     */
    @Override
    public ICustomer getCustomer (ICustomer customer, String environment, String refId)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();

        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

        customer = paymentSvc.getCustomerProfile (customer.getProfileToken(),
                                                  customer.getCustomerId(),
                                                  customer.getEmail(),
                                                  refId,
                                                  true,
                                                  true,
                                                  Profile.environment,
                                                  true
                                                 );

        return customer;
    }

    /**
     *
     * @param user
     * @param parameters
     * @param environment
     * @param refId the value of refId
     * @return the model.domain.interfaces.ICustomer
     */
    @Override
    public ICustomer createPortalProfile (IUser user, Map<String, String> parameters, String environment, String refId)
    {
        ICustomer customer;
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        IBilling billing;
        int type = IPaymentSvc.TYPE_GUEST; // TODO: make this real

        // Credit Card
        paymentSvc.createCreditCard (parameters.get ("number"),
                                     parameters.get ("expdate"),
                                     null, parameters.get ("ccv")
                                    );

        // Billing
        billing = paymentSvc.addBilling (parameters.get ("fname"),
                                         parameters.get ("lname"),
                                         parameters.get ("company"),
                                         parameters.get ("add1") + " " +
                                         parameters.get ("add2"),
                                         parameters.get ("city"),
                                         parameters.get ("state"),
                                         parameters.get ("zip"),
                                         parameters.get ("country"),
                                         parameters.get ("phone"),
                                         parameters.get ("fax")
                                        );

        //Set payee type
        if (parameters.get ("company").equals (""))
        {
            paymentSvc.addPayment (IPaymentSvc.CUST_INDIVIDUAL, billing);
        }
        else
        {
            paymentSvc.addPayment (IPaymentSvc.CUST_BUSINESS, billing);
        }

        //Customer
        customer = paymentSvc.createCustomer (parameters.get ("account"),
                                              parameters.get ("desc"),
                                              parameters.get ("email"),
                                              type,
                                              refId
                                             );

        //Set the environment
        setEnvironment (environment);

        //Create the profile
        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

        // TODO: Need to update to return an actual profile?
        customer = (ICustomer)paymentSvc.createCustomerProfile ((CustomerProfile)customer,
                                                                mode,
                                                                Profile.environment
                                                               );

        return customer;
    }

    /**
     *
     * @param transaction
     * @return
     */
    @Override
    public ICustomer getProfilePage (TransactionRecord transaction)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());
        return paymentSvc.getHostedProfilePageRequest (transaction, environment);
    }

    /**
     *
     * @param customer
     * @return
     */
    @Override
    public ICustomer deletePortalProfile (ICustomer customer)
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
        paymentSvc.setTransactionKey (params.getCfg().getTestTransId());
        customer = paymentSvc.deleteCustomerProfile (customer, environment);
        return customer;
    }

    /**
     *
     * @param environment
     */
    @Override
    public void setEnvironment (String environment)
    {
        // Select the payment processor access mode - live or test - to use
        switch (environment)
        {
            case "LIVE":
                Profile.environment = IPaymentSvc.ENV_PRODUCTION;
                this.mode = IPaymentSvc.LIVE_MODE;
                break;

            case "TEST":
            default:
                Profile.environment = IPaymentSvc.ENV_SANDBOX;
                this.mode = IPaymentSvc.TEST_MODE;
                break;
        }
    }
}
