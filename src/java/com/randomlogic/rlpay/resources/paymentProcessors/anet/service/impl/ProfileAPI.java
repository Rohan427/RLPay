/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl;

import com.randomlogic.rlpay.application.monitor.ErrorMsgBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.InternalPaymentProfile;
import com.randomlogic.rlpay.model.domain.payment.PaymentInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.BillingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.CreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.IBilling;
import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms.PaymentParms;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import static com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc.*;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ArrayOfSetting;
import net.authorize.api.contract.v1.CreateCustomerProfileRequest;
import net.authorize.api.contract.v1.CreateCustomerProfileResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.CustomerAddressType;
import net.authorize.api.contract.v1.CustomerPaymentProfileType;
import net.authorize.api.contract.v1.CustomerProfileMaskedType;
import net.authorize.api.contract.v1.CustomerProfileType;
import net.authorize.api.contract.v1.CustomerProfileTypeEnum;
import net.authorize.api.contract.v1.CustomerTypeEnum;
import net.authorize.api.contract.v1.DeleteCustomerProfileRequest;
import net.authorize.api.contract.v1.DeleteCustomerProfileResponse;
import net.authorize.api.contract.v1.GetCustomerProfileRequest;
import net.authorize.api.contract.v1.GetCustomerProfileResponse;
import net.authorize.api.contract.v1.GetHostedPaymentPageRequest;
import net.authorize.api.contract.v1.GetHostedProfilePageRequest;
import net.authorize.api.contract.v1.GetHostedProfilePageResponse;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.SettingType;
import net.authorize.api.contract.v1.ValidationModeEnum;
import net.authorize.api.controller.CreateCustomerProfileController;
import net.authorize.api.controller.DeleteCustomerProfileController;
import net.authorize.api.controller.GetCustomerProfileController;
import net.authorize.api.controller.GetHostedProfilePageController;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.model.domain.interfaces.IInternalPaymentProfile;

/**
 *
 * @author Paul.Allen
 */
public class ProfileAPI
{
    /** Customer profile data */
    private CustomerProfile customer = null;
    private String errorMessage;
    private ICreditCard creditcard;
    private InternalPaymentProfile paymentProfile;
    private BillingInfo billing;

    public ICustomer createCustomerProfile (PaymentParms payParms)
    {
        CustomerPaymentProfileType customerPaymentProfileType = new CustomerPaymentProfileType();
        CreateCustomerProfileResponse response;
        CustomerProfileType customerProfileType = new CustomerProfileType();
        CreateCustomerProfileController controller;
        CreateCustomerProfileRequest apiRequest = new CreateCustomerProfileRequest();
        CustomerProfileTypeEnum custProfileType;
        CreditCardType creditCard;
        PaymentType paymentType;
        CustomerAddressType billTo;
        Iterator profileIter;
        ErrorMsgBean errorBean = new ErrorMsgBean();

        apiRequest.setMerchantAuthentication (payParms.getEnvironment());

        // Set customer profile data
        customerProfileType.setMerchantCustomerId (payParms.getCustomer().getCustomerId());
        customerProfileType.setDescription (payParms.getCustomer().getDescription());
        customerProfileType.setEmail (payParms.getCustomer().getEmail());

        // Select customer profile type
        switch (payParms.getCustomer().getProfileType())
        {
            case TYPE_REGULAR:
                custProfileType = CustomerProfileTypeEnum.REGULAR;
                break;

            case TYPE_GUEST:
            default:
                custProfileType = CustomerProfileTypeEnum.GUEST;
                break;
        }

        customerProfileType.setProfileType (custProfileType);

        // Iterate through payment profiles and add data for each
        if (payParms.getProfileParms().getValidationMode() != NONE_MODE)
        {
            if (payParms.getCustomer().getPaymentProfiles().isEmpty())
            {
                errorBean.setErrorCode (ANET_PAYMENT_REQUIRED);
                errorBean.setErrorMessage (TRANS_PAYMENT_RQD);
            }
            else
            {
                profileIter = payParms.getCustomer().getPaymentProfiles().iterator();
                paymentProfile = (InternalPaymentProfile)profileIter.next();

                // Populate the payment data
                creditCard = new CreditCardType();
                creditCard.setCardNumber (paymentProfile.getPayment().getCreditcard().getCardNumber());
                creditCard.setExpirationDate (paymentProfile.getPayment().getCreditcard().getExpirationDate());

                if (paymentProfile.getPayment().getCreditcard().getCardCode() != null)
                {
                    creditCard.setCardCode (paymentProfile.getPayment().getCreditcard().getCardCode());
                }
                // else  Do nothing

                paymentType = new PaymentType();
                paymentType.setCreditCard (creditCard);

                // Set payment profile data
                customerPaymentProfileType.setPayment (paymentType);
                billTo = createBillTo (paymentProfile);
                customerPaymentProfileType.setBillTo (billTo);

                customerPaymentProfileType.setCustomerType (setCustomerType (paymentProfile.getCustType()));

                // Add profile to the profile list
                customerProfileType.getPaymentProfiles().add (customerPaymentProfileType);
            }
        }

        if (errorBean.getErrorCode() != ANET_PAYMENT_REQUIRED)
        {
            // Create the API request and set the parameters for this specific request
            apiRequest.setProfile (customerProfileType);

            // Select the correct validation mode
            switch (payParms.getProfileParms().getValidationMode())
            {
                case LIVE_MODE:
                    apiRequest.setValidationMode (ValidationModeEnum.LIVE_MODE);
                    break;

                case TEST_MODE:
                    apiRequest.setValidationMode (ValidationModeEnum.TEST_MODE);
                    break;

                case NONE_MODE:
                default:
                    apiRequest.setValidationMode (ValidationModeEnum.NONE);
                    break;
            }

            // Call the controller
            controller = new CreateCustomerProfileController (apiRequest);
            controller.execute();

            // Get the response
            response = controller.getApiResponse();

            // Parse the response to determine results
            if (response != null)
            {
                payParms.getCustomer().setErrorReponse (response);

                // If API Response is OK, go ahead and check the transaction response
                if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
                {
                    errorBean.setErrorMessage (response.getMessages().getMessage().get (0).getText());
                    errorBean.setErrorCode (TRANSACTION_SUCCESS);

                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "refId - " + response.getRefId());
                    payParms.getCustomer().setRefId (response.getRefId());

                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "customerId - " + response.getCustomerProfileId());
                    payParms.getCustomer().setProfileToken (response.getCustomerProfileId());

                    if (!response.getCustomerPaymentProfileIdList().getNumericString().isEmpty())
                    {
                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Payment list - " + response.getCustomerPaymentProfileIdList().getNumericString().get(0));
                    }
                    // else  Do nothing

                    if (!response.getCustomerShippingAddressIdList().getNumericString().isEmpty())
                    {
                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Shipping list - " + response.getCustomerShippingAddressIdList().getNumericString().get(0));
                    }
                    // else Do nothing

                    if (!response.getValidationDirectResponseList().getString().isEmpty())
                    {
                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Validation list - " + response.getValidationDirectResponseList().getString().get(0));
                    }
                    // else Do nothing
                }
                else
                {
                    Logger.log (this.getClass(), ANET_PROFILE_CREATE, response.getMessages().getMessage().get (0).getText());
                    errorBean.setErrorMessage (response.getMessages().getMessage().get (0).getText());
                    errorBean.setErrorCode (ANET_PROFILE_CREATE);
                }
            }
            else
            {
                // Display the error code and message when response is null
                ANetApiResponse errorResponse = controller.getErrorResponse();
                Logger.log (this.getClass(), ANET_PROFILE_CREATE, "Failed to get response");

                if (!errorResponse.getMessages().getMessage().isEmpty())
                {
                    Logger.log (this.getClass(), ANET_PROFILE_CREATE, TRANS_PROF_CREATE_FAILURE);

                    errorBean.setErrorMessage (TRANS_PROF_CREATE_FAILURE);
                    errorBean.setErrorCode (ANET_PROFILE_CREATE);
                }
                else
                {
                    Logger.log (this.getClass(), ANET_PROFILE_CREATE, errorResponse.getMessages().getMessage().get (0).getText());

                    errorBean.setErrorMessage (errorResponse.getMessages().getMessage().get (0).getText());
                    errorBean.setErrorCode (ANET_PROFILE_CREATE);
                }
            }
        }// END IF-ELSE: if (customer.getPaymentProfiles().isEmpty())

        payParms.getCustomer().setErrorReponse (errorBean);
        return payParms.getCustomer();
    }

    public ICustomer deleteCustomerProfile (PaymentParms payParms)
    {
        DeleteCustomerProfileRequest apiRequest = new DeleteCustomerProfileRequest();
        DeleteCustomerProfileController controller;
        DeleteCustomerProfileResponse response;
        ErrorMsgBean errorBean = new ErrorMsgBean();

        customer = (CustomerProfile)payParms.getCustomer();
        apiRequest.setMerchantAuthentication (payParms.getEnvironment());
        apiRequest.setCustomerProfileId (customer.getProfileToken());

        controller = new DeleteCustomerProfileController (apiRequest);
        controller.execute();

        response = controller.getApiResponse();

        if (response!=null)
        {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get(0).getText());

                customer.setProfileToken ("0");
                customer.setPaymentProfiles (new ArrayList<>());
                customer.setRefId (response.getRefId());
                errorBean.setErrorCode (TRANSACTION_SUCCESS);
                errorBean.setErrorMessage (response.getMessages().getMessage().get (0).getText());
           }
           else
           {
               customer.setRefId (response.getRefId());
               Logger.log (this.getClass(), ANET_PROFILE_DELETE, response.getMessages().getMessage().get (0).getText());
               errorBean.setErrorCode (ANET_PROFILE_DELETE);
               errorBean.setErrorMessage (response.getMessages().getMessage().get (0).getText());
           }
        }
        else
        {
            Logger.log (this.getClass(), ANET_PROFILE_DELETE, response.getMessages().getMessage().get (0).getText());

            try
            {
                customer.setRefId (controller.getErrorResponse().getRefId());
                errorBean.setErrorCode (ANET_PROFILE_DELETE);
                errorBean.setErrorMessage (controller.getErrorResponse().getMessages().getMessage().get (0).getText());
            }
            catch (NullPointerException e)
            {
                errorBean.setErrorMessage (TRANS_DELETE_FAILED);
                errorBean.setErrorCode (TRANSACTION_RESP_UKNWN);
            }
        }

        customer.setErrorReponse (errorBean);
        return customer;
    }

    public ICustomer getCustomerProfile (PaymentParms payParms)
    {
        GetCustomerProfileResponse response;
        GetCustomerProfileController controller;
        GetCustomerProfileRequest apiRequest = new GetCustomerProfileRequest();
        ErrorMsgBean errorBean = new ErrorMsgBean();
        int incompleteData = 0;

        customer = new CustomerProfile();
        apiRequest.setMerchantAuthentication (payParms.getEnvironment());

        // validate input
        if (payParms.getProfileParms().getCustomerProfileId() == null)
        {
            payParms.getProfileParms().setCustomerProfileId ("0");
        }

        if (payParms.getProfileParms().getCustomerId() == null)
        {
            payParms.getProfileParms().setCustomerId ("0");
        }

        if (!payParms.getProfileParms().getCustomerProfileId().equals ("0"))
        {
            apiRequest.setCustomerProfileId (payParms.getProfileParms().getCustomerProfileId());
            incompleteData += 2;
        }
        else if (!payParms.getProfileParms().getCustomerId().equals ("0"))
        {
            apiRequest.setMerchantCustomerId (payParms.getProfileParms().getCustomerId());
            incompleteData += 1;

            if (payParms.getProfileParms().getEmail() != null)
            {
                if (!payParms.getProfileParms().getEmail().isEmpty())
                {
                    apiRequest.setEmail (payParms.getProfileParms().getEmail());
                    incompleteData += 1;
                }
                //else do nothing
            }
            // else do nothing
        }
        // else Do nothing

        if (payParms.getProfileParms().getRefId() != null)
        {
            apiRequest.setRefId (payParms.getProfileParms().getRefId());
        }
        //else do nothing

        // Must either have a valid profile ID or both customer ID and e-mail
        if (incompleteData >= 2)
        {
            apiRequest.setUnmaskExpirationDate (payParms.getProfileParms().isUnmaskExpirationDate());
            apiRequest.setIncludeIssuerInfo (payParms.getProfileParms().isIncludeIssuerInfo());

            // Make the request
            controller = new GetCustomerProfileController (apiRequest);
            controller.execute();

            response = controller.getApiResponse();

            customer.setErrorReponse (response);

            if (response != null)
            {
                if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
                {
                    CustomerProfileMaskedType profile = response.getProfile();

                    if (payParms.getProfileParms().getRefId().equals (response.getRefId()))
                    {
                        // Set the basic custoimer profile data
                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get(0).getText());
                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, "CustomerID:\t" + profile.getMerchantCustomerId());

                        customer.setCustomerId (profile.getMerchantCustomerId());
                        customer.setRefId (response.getRefId());

                        if (!payParms.getProfileParms().isSecure())
                        {
                            Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Description:\t" + profile.getDescription());
                            Logger.log (this.getClass(), TRANSACTION_SUCCESS, "E-mail:\t" + profile.getEmail());

                            customer.setEmail (profile.getEmail());
                            customer.setDescription (profile.getDescription());
                        }
                        //Secure transaction, do not get PII

                        Logger.log (this.getClass(), TRANSACTION_SUCCESS, "ProfileID:\t" + profile.getCustomerProfileId());

                        customer.setProfileToken (profile.getCustomerProfileId());

                        // TODO: parse all possible payment profiles
                        if ((!profile.getPaymentProfiles().isEmpty()))
                        {
                            // Set the credit card data
                            if (!payParms.getProfileParms().isSecure())
                            {
                                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Card Number:\t" + profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getCardNumber());
                                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Expiration:\t" + profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getExpirationDate());
                                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Card Type:\t" + profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getCardType());

                                addCreditCard (new CreditCard (profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getCardNumber(),
                                                               profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getExpirationDate(),
                                                               profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getCardType(),
                                                               "0"
                                                              )
                                              );
                            }
                            else
                            {
                                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "SECURE: Card suppressed");

                                addCreditCard (new CreditCard (profile.getPaymentProfiles().get (0).getPayment().getCreditCard().getCardNumber(),
                                                               null,
                                                               null,
                                                               "0"
                                                              )
                                              );
                            }

                            // Get the billing data, if available
                            if (!payParms.getProfileParms().isSecure())
                            {
                                if (profile.getPaymentProfiles().get (0).getBillTo() != null)
                                {
                                    // Set the billing data
                                    CustomerAddressType billTo = profile.getPaymentProfiles().get (0).getBillTo();

                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "First Name:\t" + billTo.getFirstName());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Last Name:\t" + billTo.getLastName());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Company:\t" + billTo.getCompany());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Address:\t" + billTo.getAddress());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "City:\t" + billTo.getCity());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "State:\t" + billTo.getState());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Postal Code:\t" + billTo.getZip());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Country:\t" + billTo.getCountry());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Phone:\t" + billTo.getPhoneNumber());
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "FAX:\t" + billTo.getFaxNumber());

                                    addBilling (billTo.getFirstName(), billTo.getLastName(),
                                                billTo.getCompany(), billTo.getAddress(),
                                                billTo.getCity(), billTo.getState(),
                                                billTo.getZip(), billTo.getCountry(),
                                                billTo.getPhoneNumber(), billTo.getFaxNumber()
                                               );
                                }// ENDIF: if (profile.getPaymentProfiles().get(0).getBillTo() != null)
                                else
                                {
                                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "No Billing Data");

                                    addBilling (null, null,
                                                null, null,
                                                null, null,
                                                null, null,
                                                null, null
                                               );
                                }
                            }
                            else
                            {
                                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "SECURE: Billing suppressed");

                                addBilling (null, null,
                                            null, null,
                                            null, null,
                                            null, null,
                                            null, null
                                           );
                            }

                            // TODO: Set the payment profile
                            addPayment (IPaymentSvc.CUST_INDIVIDUAL, billing);

                            Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Payment Profile ID:\t" + profile.getPaymentProfiles().get (0).getCustomerPaymentProfileId());

                            paymentProfile.setProfileId (profile.getPaymentProfiles().get (0).getCustomerPaymentProfileId());
                            paymentProfile.setCustType (convertCustomerType (profile.getPaymentProfiles().get (0).getCustomerType()));

                            /* Compensate for Auth.Net bug. 11/16/2018
                               The flag can be null even though it is supposed to be either true or false.
                               Since the only access to retrieve it is isDefaultPaymentProfile() and this may return null,
                               we need to check for a null value. If it's null, we'll set it to false in the profile.
                            */
                            try
                            {
                                paymentProfile.setDefaultProfile (profile.getPaymentProfiles().get (0).isDefaultPaymentProfile());
                            }
                            catch (NullPointerException e)
                            {
                                paymentProfile.setDefaultProfile (false);
                            }

                            // Add payment profile to customer object
                            customer.addPaymentProfile (paymentProfile);
                        }// ENDIF: if ((!profile.getPaymentProfiles().isEmpty()))
                        // else do nothing
                    }
                    else
                    {
                        Logger.log (this.getClass(), ANET_PROFILE_LOAD, TRANS_GUID_MISMATCH);

                        customer.setProfileToken ("0");// Invalid profile
                        errorBean.setErrorMessage (TRANS_GUID_MISMATCH);
                        errorBean.setErrorCode (TRANSACTION_GUID_ERR);
                    }

                }// ENDIF: if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
                else
                {
                    Logger.log (this.getClass(), ANET_PROFILE_LOAD, TRANS_PROF_PROFILE_FAILURE + ": " + response.getMessages().getResultCode());

                    errorBean.setErrorMessage (TRANS_PROF_PROFILE_FAILURE);
                    errorBean.setErrorCode (ANET_PROFILE_LOAD);
                }
            }// ENDIF: if (response!=null)
            else
            {
                customer.setProfileToken ("0"); // Invalid profile

                try
                {
                    customer.setRefId (controller.getErrorResponse().getRefId());
                    errorBean.setErrorMessage (controller.getErrorResponse().getMessages().getMessage().get (0).getText());
                }
                catch (NullPointerException e)
                {
                    errorBean.setErrorMessage (TRANS_PROF_PROFILE_FAILURE);
                    errorBean.setErrorCode (TRANSACTION_RESP_UKNWN);
                }
            }
        }
        else
        {
            errorBean.setErrorMessage (TRANS_AUTH_NO_PROFILE);
            errorBean.setErrorCode (ANET_PROFILE_LOAD);
        }

        customer.setErrorReponse (errorBean);
        return customer;
    }

    public ICustomer getHostedProfilePageRequest (PaymentParms payParms)
    {
        TransactionRecord transaction = payParms.getTransaction();
        GetHostedProfilePageResponse response;
        GetHostedProfilePageController controller;
        GetHostedProfilePageRequest apiRequest = new GetHostedProfilePageRequest();
        ErrorMsgBean errorBean = new ErrorMsgBean();

        apiRequest.setMerchantAuthentication (payParms.getEnvironment());

        // Configure hosted page settings
        apiRequest = (GetHostedProfilePageRequest)configureHostedPaymentSettings (apiRequest, transaction.getRequest().getPaymentSettings(), "profile");

        apiRequest.setCustomerProfileId (transaction.getRequest().getCustomer().getProfileToken());

        controller = new GetHostedProfilePageController (apiRequest);
        controller.execute();

        response = controller.getApiResponse();

        if (response!=null)
        {
             if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
             {
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get (0).getText());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Hosted Page Token - " + response.getToken());

                transaction.getRequest().getCustomer().setToken (response.getToken());
            }
            else
            {
                Logger.log (this.getClass(), ANET_PROFILE_LOAD, response.getMessages().getMessage().get (0).getText());

                if (response.getMessages().getMessage().get (0).getCode().equals ("E00013"))
                {
                    if (!response.getMessages().getMessage().get (0).getText().contains ("Name"))
                    {
                        errorBean.setErrorCode (ANET_INVALID_SETTING_VALUE);
                        errorBean.setErrorMessage (TRANS_INVALID_VALUE);
                    }
                    else
                    {
                        errorBean.setErrorCode (ANET_INVALID_SETTING_NAME);
                        errorBean.setErrorMessage (TRANS_INVALID_NAME);
                    }
                }
                else
                {
                    if (!response.getMessages().getMessage().get (0).getText().contains ("Name"))
                    {
                        errorBean.setErrorCode (ANET_NO_SETTING_VALUE);
                        errorBean.setErrorMessage (TRANS_SETTING_VALUE);
                    }
                    else
                    {
                        errorBean.setErrorCode (ANET_NO_SETTING_NAME);
                        errorBean.setErrorMessage (TRANS_SETTING_NAME);
                    }
                }
            }
        }
        else
        {
            Logger.log (this.getClass(), ANET_PROFILE_PAGE_LOAD, TRANS_PAGE_LOAD_FAILURE);
            errorBean.setErrorCode (TRANSACTION_RESP_UKNWN);

            try
            {
                transaction.setRefId (controller.getErrorResponse().getRefId());
                errorBean.setErrorMessage (controller.getErrorResponse().getMessages().getMessage().get (0).getText());
            }
            catch (NullPointerException e)
            {
                errorBean.setErrorMessage (TRANS_PAGE_LOAD_FAILURE);
            }
        }

        transaction.getRequest().getCustomer().setErrorReponse (errorBean);
        return transaction.getRequest().getCustomer();
    }

    private IBilling addBilling (String firstName, String lastName,
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

    private void addCreditCard (ICreditCard creditcard)
    {
        this.creditcard = (CreditCard)creditcard;
    }

    private boolean addPayment (int type,
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

    private int convertCustomerType (CustomerTypeEnum custType)
    {
        int type;

        if (custType != null)
        {
            switch (custType)
            {

                case BUSINESS:
                    type = IPaymentSvc.CUST_BUSINESS;
                    break;

                case INDIVIDUAL:
                default:
                    type = IPaymentSvc.CUST_INDIVIDUAL;
                    break;
            }
        }
        else
        {
            type = IPaymentSvc.CUST_INDIVIDUAL;
        }

        return type;
    }

    private CustomerAddressType createBillTo (IInternalPaymentProfile paymentProfile)
    {
        CustomerAddressType billTo = new CustomerAddressType();
        billTo.setFirstName (paymentProfile.getBilling().getFirstName());
        billTo.setLastName (paymentProfile.getBilling().getLastName());
        billTo.setCompany (paymentProfile.getBilling().getCompany());
        billTo.setAddress (paymentProfile.getBilling().getAddress());
        billTo.setCity (paymentProfile.getBilling().getCity());
        billTo.setState (paymentProfile.getBilling().getState());
        billTo.setZip (paymentProfile.getBilling().getZip());
        billTo.setCountry (paymentProfile.getBilling().getCountry());
        billTo.setPhoneNumber (paymentProfile.getBilling().getPhoneNumber());
        billTo.setFaxNumber (paymentProfile.getBilling().getFaxNumber());

        return billTo;
    }

    /**
     *
     * @param apiRequest the value of apiRequest
     * @param paymentSettings the value of paymentSettings
     * @param type the value of type
     */
    private Object configureHostedPaymentSettings (Object apiRequest, Map<String, String> paymentSettings, String type)
    {
        SettingType setting;
        ArrayOfSetting alist = new ArrayOfSetting();

        for (Map.Entry<String, String> entry : paymentSettings.entrySet())
        {
            setting = new SettingType();
            setting.setSettingName (entry.getKey());
            setting.setSettingValue (entry.getValue());
            alist.getSetting().add (setting);
        }

        switch (type)
        {
            case "profile":
                {
                    GetHostedProfilePageRequest request = (GetHostedProfilePageRequest)apiRequest;
                    request.setHostedProfileSettings (alist);
                    return request;
                }

            case "payment":
            default:
                {
                    GetHostedPaymentPageRequest request = (GetHostedPaymentPageRequest)apiRequest;
                    request.setHostedPaymentSettings (alist);
                    return request;
                }
        }
    }

    private CustomerTypeEnum setCustomerType (int type)
    {
        CustomerTypeEnum custType = null;

        switch (type)
        {
            case IPaymentSvc.CUST_BUSINESS:
                custType = CustomerTypeEnum.BUSINESS;
                break;

            case IPaymentSvc.CUST_INDIVIDUAL:
            default:
                custType = CustomerTypeEnum.INDIVIDUAL;
                break;
        }

        return custType;
    }
}
