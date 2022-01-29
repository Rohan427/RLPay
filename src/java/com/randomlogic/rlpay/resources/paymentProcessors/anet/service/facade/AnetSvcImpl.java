/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade;

import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.InternalPaymentProfile;
import com.randomlogic.rlpay.model.domain.payment.PaymentInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.BillingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.CreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms.PaymentParms;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl.PaymentAPI;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl.ProfileAPI;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl.ReportAPI;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public class AnetSvcImpl implements IPaymentSvc
{
    // Payment portal access settings
    private String apiLoginId = null;
    private String transactionKey = null;
    private String customerId = "0";
    private String profileToken = "0";
    private String description = "INVALID";
    private String email = "nobody@nowhere.com";

    /** Customer profile data */
    private CustomerProfile customer = null;
    private String errorMessage;
    private ICreditCard creditcard;
    private InternalPaymentProfile paymentProfile;
    private BillingInfo billing;

    public AnetSvcImpl()
    {
        super();
    }

    public AnetSvcImpl (String apiLoginId,
                           String transactionKey,
                           String email,
                           String customerId,
                           String description
                          )
    {
        this.apiLoginId = apiLoginId;
        this.transactionKey = transactionKey;
        this.email = email;
        this.customerId = customerId;
        this.description = description;


    }

    @Override
    public String getApiLoginId()
    {
        return this.apiLoginId;
    }

    @Override
    public void setApiLoginId (String apiLoginId)
    {
        this.apiLoginId = apiLoginId;
    }

    @Override
    public String getTransactionKey()
    {
        return this.transactionKey;
    }

    @Override
    public void setTransactionKey (String transactionKey)
    {
        this.transactionKey = transactionKey;
    }

    @Override
    public String getEmail()
    {
        return this.email;
    }

    @Override
    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String getCustomerId()
    {
        return this.customerId;
    }

    @Override
    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    @Override
    public String getProfileToken()
    {
        return this.profileToken;
    }

    @Override
    public void setProfileToken (String profileToken)
    {
        this.profileToken = profileToken;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public void setDescription (String description)
    {
        this.description = description;
    }

    public MerchantAuthenticationType setMerchantAuthType (int environment)
    {
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType();

        // Select the correct envirnment for this implementation
        switch (environment)
        {
            case ENV_PRODUCTION:
                ApiOperationBase.setEnvironment (Environment.PRODUCTION);
                break;

            case ENV_SANDBOX:
            default:
                ApiOperationBase.setEnvironment (Environment.SANDBOX);
                break;
        }

        // Create object with merchant authentication details
        merchantAuthenticationType.setName (apiLoginId);
        merchantAuthenticationType.setTransactionKey (transactionKey);

        return merchantAuthenticationType;
    }

    @Override
    public ICustomer createCustomer (String customerId, String description,
                                     String email, int profileType,
                                     String refId
                                    )
    {
        customer = new CustomerProfile();

        customer.setCustomerId (customerId);
        customer.setDescription (description);
        customer.setEmail (email);
        customer.setProfileType (profileType);
        customer.setRefId (refId);

////        customer.addPaymentProfile (paymentProfile);

        return customer;
    }

    /**
     *
     * @param customer
     * @param validationMode
     * @param environment
     * @return
     */
    @Override
    public ICustomer createCustomerProfile (ICustomer customer,
                                            int validationMode,
                                            int environment
                                           )
    {
        ProfileAPI api = new ProfileAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setCustomer (customer);
        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.getProfileParms().setValidationMode (validationMode);

        return api.createCustomerProfile (payParms);
    }

    /**
     *
     * @param customer
     * @param environment
     * @return
     */
    @Override
    public ICustomer deleteCustomerProfile (ICustomer customer, int environment)
    {
        ProfileAPI api = new ProfileAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.setCustomer (customer);
        return api.deleteCustomerProfile (payParms);
    }

    @Override
    public ICustomer getCustomerProfile (String customerProfileId,
                                         String customerId,
                                         String email,
                                         String refId,
                                         boolean unmaskExpirationDate,
                                         boolean includeIssuerInfo,
                                         int environment,
                                         boolean secure
                                        )
    {
        ProfileAPI api = new ProfileAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.getProfileParms().setCustomerId (customerId);
        payParms.getProfileParms().setCustomerProfileId (customerProfileId);
        payParms.getProfileParms().setEmail (email);
        payParms.getProfileParms().setIncludeIssuerInfo (includeIssuerInfo);
        payParms.getProfileParms().setRefId (refId);
        payParms.getProfileParms().setUnmaskExpirationDate (unmaskExpirationDate);
        payParms.getProfileParms().setSecure (secure);

        return api.getCustomerProfile (payParms);
    }

    @Override
    public ICustomer getHostedProfilePageRequest (TransactionRecord transaction,
                                                   int environment
                                                  )
    {
        ProfileAPI api = new ProfileAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.setTransaction (transaction);

        return api.getHostedProfilePageRequest (payParms);
    }

    /**
     *
     * @param transaction
     * @param environment
     * @return
     */
    @Override
    public TransactionRecord getHostedPaymentPageRequest (TransactionRecord transaction,
                                                          int environment
                                                         )
    {
        PaymentAPI api = new PaymentAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.setTransaction (transaction);

        return api.getHostedPaymentPageRequest (payParms);
    }

    @Override
    public TransactionRecord executePaymentRequest (TransactionRecord transaction,
                                                    int environment
                                                   )
    {
        PaymentAPI api = new PaymentAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.setTransaction (transaction);

        return api.executePaymentRequest (payParms);
    }

    @Override
    public IPayTransactionReport getTransactionDetails (TransactionRecord transaction,
                                                        int environment
                                                       )
    {
        ReportAPI api = new ReportAPI();
        PaymentParms payParms = new PaymentParms();

        payParms.setEnvironment (setMerchantAuthType (environment));
        payParms.setTransaction (transaction);

        return api.getTransactionDetails (payParms);
    }

    @Override
    public IBilling addBilling (String firstName, String lastName,
                                String company, String address,
                                String city, String state,
                                String zip, String country,
                                String phoneNumber, String faxNumber
                               )
    {
        billing = new BillingInfo (firstName, lastName,
                                   company, address,
                                   city, state,
                                   zip, country,
                                   phoneNumber, faxNumber
                                  );

        return billing;
    }

    @Override
    public boolean addPayment (int type,
                               IBilling billing
                              )
    {
        boolean result = true;
        PaymentInfo payment = new PaymentInfo();

        payment.setCreditcard (creditcard);
        paymentProfile = new InternalPaymentProfile (type,
                                                     (BillingInfo)billing,
                                                     payment
                                                    );

        return result;
    }

    @Override
    public void addCreditCard (ICreditCard creditcard)
    {
        this.creditcard = (CreditCard)creditcard;
    }

    @Override
    public String getErrorMessage()
    {
        return errorMessage;
    }

    @Override
    public void createCreditCard (String cardNumber, String expirationDate,
                                  String cardType, String cardCode)
    {
        creditcard = new CreditCard (cardNumber, expirationDate,
                                     cardType, cardCode);
    }
}
