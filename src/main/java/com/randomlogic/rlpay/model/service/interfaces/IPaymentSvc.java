/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public interface IPaymentSvc extends Serializable
{
    // Validation modes
    public static final int TEST_MODE       = 0;
    public static final int LIVE_MODE       = 1;
    public static final int NONE_MODE       = 3;

    // Environments
    public static final int ENV_SANDBOX     = 0;
    public static final int ENV_PRODUCTION  = 1;

    // Customer Types
    public static final int CUST_INDIVIDUAL = 0;
    public static final int CUST_BUSINESS   = 1;

    // Profile Types (for editing)
    public static final int MANAGE_PROFILE  = 0;
    public static final int ADD_PAYMENT     = 1;
    public static final int ADD_SHIPPING    = 2;
    public static final int EDIT_PAYMENT    = 3;
    public static final int EDIT_SHIPPING   = 4;

    // Customer Profile Type
    public static final int TYPE_INVALID    = 0;
    public static final int TYPE_GUEST      = 1;
    public static final int TYPE_REGULAR    = 2;

    //Transaction Types
    public static final int TTYPE_AUTH_ONLY             = 1;
    public static final int TTYPE_AUTH_CAPTURE          = 2;
    public static final int TTYPE_CAPTURE_ONLY          = 3;
    public static final int TTYPE_REFUND                = 4;
    public static final int TTYPE_PRIOR_AUTH_CAPTURE    = 5;
    public static final int TTYPE_VOID                  = 6;
    public static final int TTYPE_GET_DETAILS           = 7;
    public static final int TTYPE_AUTH_ONLY_CONTINUE    = 8;
    public static final int TTYPE_AUTH_CAPTURE_CONTINUE = 9;

    ICustomer createCustomerProfile (ICustomer customer,
                                     int validationMode,
                                     int environment
                                    );

    /**
     *
     * @param customerProfileId the value of customerProfileId
     * @param customerId the value of customerId
     * @param email the value of email
     * @param refId the value of refId
     * @param unmaskExpirationDate the value of unmaskExpirationDate
     * @param includeIssuerInfo the value of includeIssuerInfo
     * @param environment the value of environment
     * @param secure the value of secure
     * @return
     */
    ICustomer getCustomerProfile (String customerProfileId,
                                  String customerId,
                                  String email,
                                  String refId,
                                  boolean unmaskExpirationDate,
                                  boolean includeIssuerInfo,
                                  int environment,
                                  boolean secure
                                 );

    ICustomer createCustomer (String customerId, String description,
                              String email, int profileType,
                              String refId
                             );

    IBilling addBilling (String firstName, String lastName,
                             String company, String address,
                             String city, String state,
                             String zip, String country,
                             String phoneNumber, String faxNumber
                            );

    boolean addPayment (int type, IBilling billing);

    void addCreditCard (ICreditCard creditcard);

    ICustomer getHostedProfilePageRequest (TransactionRecord transaction,
                                           int environment
                                          );

    ICustomer deleteCustomerProfile (ICustomer customer, int environment);

    TransactionRecord getHostedPaymentPageRequest (TransactionRecord transaction,
                                                   int environment
                                                  );

    TransactionRecord executePaymentRequest (TransactionRecord transaction,
                                             int environment
                                            );

    IPayTransactionReport getTransactionDetails (TransactionRecord transaction,
                                                 int environment
                                                );

    String getErrorMessage();

    String getApiLoginId();

    String getCustomerId();

    String getDescription();

    String getEmail();

    String getProfileToken();

    String getTransactionKey();

    void setApiLoginId (String apiLoginId);

    void setCustomerId (String customerId);

    void setDescription (String description);

    void setEmail (String email);

    void setProfileToken (String profileToken);

    void setTransactionKey (String transactionKey);

    public void createCreditCard (String cardNumber, String expirationDate,
                                  String cardType, String cardCode
                                 );
}
