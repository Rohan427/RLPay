/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.TransactionSetting;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.IAccount;
import com.randomlogic.rlpay.model.domain.interfaces.IInvoice;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms.PaymentParms;
import static com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc.*;
import com.randomlogic.rlpay.model.service.interfaces.ITransactionSetting;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ArrayOfLineItem;
import net.authorize.api.contract.v1.ArrayOfSetting;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.CustomerDataType;
import net.authorize.api.contract.v1.CustomerProfilePaymentType;
import net.authorize.api.contract.v1.GetHostedPaymentPageRequest;
import net.authorize.api.contract.v1.GetHostedPaymentPageResponse;
import net.authorize.api.contract.v1.GetHostedProfilePageRequest;
import net.authorize.api.contract.v1.LineItemType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.OpaqueDataType;
import net.authorize.api.contract.v1.OrderType;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.SettingType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.GetHostedPaymentPageController;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;
import com.randomlogic.rlpay.portal.payment.domain.TransRequest;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public class PaymentAPI
{
    /** Customer profile data */
    private CustomerProfile customer = null;
////    private String errorMessage;
////    private ICreditCard creditcard;
////    private InternalPaymentProfile paymentProfile;
////    private BillingInfo billing;

    public TransactionRecord executePaymentRequest (PaymentParms payParms)
    {
        TransactionRecord transaction = payParms.getTransaction();
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        PaymentType paymentType = new PaymentType();
        TransactionRequestType txnRequest = new TransactionRequestType();
        CreateTransactionController controller;
        OrderType order = new OrderType();
        CreateTransactionResponse response;
        OpaqueDataType paymentData = new OpaqueDataType();
        Logs log = new Logs();

        log.setErrorSource (this.getClass().toString());
        log.setMethod ("payment");
        log.setCommand ("payment");

        // Set the request to operate in either the sandbox or production environment
        apiRequest.setMerchantAuthentication (payParms.getEnvironment());
        apiRequest.setRefId (transaction.getRequest().getCustomer().getRefId());

        paymentData.setDataDescriptor (transaction.getRequest().getNonce().getDescription());
        paymentData.setDataValue (transaction.getRequest().getNonce().getNonce());
        paymentType.setOpaqueData (paymentData);

        // Set customer data
        CustomerDataType customerType = new CustomerDataType();
        customerType.setEmail (transaction.getRequest().getCustomer().getEmail());
        customerType.setId (transaction.getRequest().getCustomer().getCustomerId());
        txnRequest.setCustomer (customerType);

        // Create the order options
        order.setInvoiceNumber (transaction.getRequest().getTransInvoiceNo());
        order.setDescription (transaction.getRequest().getDescription());
        txnRequest.setOrder (order);

        // Create the line items
        txnRequest = createLineItemList (txnRequest, transaction.getRequest());
        apiRequest.setTransactionRequest (txnRequest);

        // Create the payment transaction object
        txnRequest.setTransactionType (getTransactionType (transaction.getRequest().getTransactionType()).value());
        txnRequest.setPayment (paymentType);
        txnRequest.setAmount (new BigDecimal (transaction.getRequest().getAmount()).setScale (2, RoundingMode.CEILING));

        // Create transaction settings list
        txnRequest = createTransactionSettings (txnRequest, transaction.getRequest().getTransactionSettings());

        // Create the API request
        apiRequest.setTransactionRequest (txnRequest);

        // Call the controller
        controller = new CreateTransactionController (apiRequest);
        controller.execute();

        // Get the response
        response = controller.getApiResponse();

        // Parse the response to determine results
        if (response != null)
        {
            transaction.getRequest().getCustomer().setErrorReponse (response);

            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                TransactionResponse result = response.getTransactionResponse();

                transaction.getResponse().setTransactionId (result.getTransId());
                transaction.getResponse().setAuthCode (result.getAuthCode());
                transaction.getResponse().setResponseCode (result.getResponseCode());

                if (result.getMessages() != null)
                {
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get (0).getText());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Transaction ID: " + result.getTransId());

                    transaction.getResponse().setErrorCode (TRANSACTION_SUCCESS);
                    transaction.getResponse().setErrorMessage (response.getMessages().getMessage().get (0).getText());
                    transaction.getResponse().setDescription (result.getMessages().getMessage().get (0).getDescription());

                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Successfully created transaction with Transaction ID: " + result.getTransId());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Response Code: " + result.getResponseCode());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Message Code: " + result.getMessages().getMessage().get (0).getCode());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Description: " + result.getMessages().getMessage().get (0).getDescription());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Auth Code: " + result.getAuthCode());
                }
                else
                {
                    Logger.log (this.getClass(), ANET_PAYMENT_FAILED, response.getMessages().getMessage().get (0).getText());

                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "FAILED Transaction ID: " + result.getTransId());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Response Code: " + result.getResponseCode());
                    Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Auth Code: " + result.getAuthCode());

                    transaction.getResponse().setErrorMessage (response.getMessages().getMessage().get (0).getText());
                    transaction.getResponse().setErrorCode (ANET_PAYMENT_PAGE_LOAD);

                    Logger.log (this.getClass(), "Failed Transaction.");

                    if (response.getTransactionResponse().getErrors() != null)
                    {
                        transaction.getResponse().setErrorCode (ANET_PAYMENT_FAILED);
                        transaction.getResponse().setErrorMessage (response.getTransactionResponse().getErrors().getError().get (0).getErrorText());

                        Logger.log (this.getClass(), TRANSACTION_FAILED, "Error Code: " + response.getTransactionResponse().getErrors().getError().get (0).getErrorCode());
                        Logger.log (this.getClass(), TRANSACTION_FAILED, "Error message: " + response.getTransactionResponse().getErrors().getError().get (0).getErrorText());
                    }
                    else
                    {
                        transaction.getResponse().setErrorMessage (TRANS_PAY_FAILED);
                        transaction.getResponse().setErrorCode (TRANSACTION_RESP_UKNWN);
                    }
                }
            }
            else
            {
                transaction.getResponse().setDescription (PAY_PROCESSING_FAIL);

                if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null)
                {
                    transaction.getResponse().setErrorCode (ANET_PAYMENT_FAILED);
                    transaction.getResponse ().setErrorMessage (response.getTransactionResponse().getErrors().getError().get (0).getErrorCode() + ": " +
                                                                response.getTransactionResponse().getErrors().getError().get (0).getErrorText());

                    Logger.log (this.getClass(), "Error Code: " + response.getTransactionResponse().getErrors().getError().get (0).getErrorCode());
                    Logger.log (this.getClass(), "Error message: " + response.getTransactionResponse().getErrors().getError().get (0).getErrorText());
                }
                else
                {
                    transaction.getResponse().setErrorCode (ANET_PAYMENT_FAILED);
                    transaction.getResponse ().setErrorMessage (response.getMessages().getMessage().get (0).getCode() + ": " +
                                                                response.getMessages().getMessage().get (0).getText());

                    Logger.log (this.getClass(), "Error Code: " + response.getMessages().getMessage().get (0).getCode());
                    Logger.log (this.getClass(), "Error message: " + response.getMessages().getMessage().get (0).getText());
                }
            }
        }
        else
        {
            // Display the error code and message when response is null
            ANetApiResponse errorResponse = controller.getErrorResponse();
            Logger.log (this.getClass(), "Failed to get response");

            if (!errorResponse.getMessages().getMessage().isEmpty())
            {
                transaction.getResponse().setErrorCode (ANET_PAYMENT_FAILED);
                transaction.getResponse ().setErrorMessage (errorResponse.getMessages().getMessage().get(0).getCode() + ": " +
                                                            errorResponse.getMessages().getMessage().get(0).getText());

                Logger.log (this.getClass(), "Error: " + errorResponse.getMessages().getMessage().get(0).getCode() +
                                             "\n" + errorResponse.getMessages().getMessage().get(0).getText());
            }
            else
            {
                transaction.getResponse().setErrorCode (ANET_PAYMENT_FAILED);
                transaction.getResponse ().setErrorMessage ("Payment Processor failed to send response");
            }
        }

        log.setTransactionId (transaction.getResponse().getTransactionId());
        log.setAuthCode (transaction.getResponse().getAuthCode());
        log.setAmount ("" + transaction.getRequest().getAmount());
        log.setCustomerId (transaction.getRequest().getCustomer().getCustomerId());
        log.setErrorCode ("" + transaction.getResponse().getErrorCode());
        log.setErrorMsg (transaction.getResponse().getErrorMessage());
        log.setErrorType (ANET_TRANS_ERROR);
        log.setLogText (transaction.getResponse().getDescription());
        log.setGuid (transaction.getRequest().getCustomer().getRefId());

        Logger.log (0, this.getClass(), log, true);

        return transaction;
    }

    public TransactionRecord getHostedPaymentPageRequest (PaymentParms payParms)
    {
        TransactionRecord transaction = payParms.getTransaction();
        GetHostedPaymentPageRequest apiRequest = new GetHostedPaymentPageRequest();
        CustomerProfilePaymentType profile = new CustomerProfilePaymentType();
        TransactionRequestType txnRequest = new TransactionRequestType();
        OrderType order = new OrderType();
        GetHostedPaymentPageController controller;
        GetHostedPaymentPageResponse response;

        apiRequest.setMerchantAuthentication (payParms.getEnvironment());
        apiRequest.setRefId (transaction.getRequest().getCustomer().getRefId());

        // Create the payment transaction request
        txnRequest.setTransactionType (getTransactionType (transaction.getRequest().getTransactionType()).value());
        txnRequest.setAmount (new BigDecimal (transaction.getRequest().getAmount()).setScale (2, RoundingMode.CEILING));

        // Create the order options
        order.setInvoiceNumber (transaction.getRequest().getTransInvoiceNo());
        order.setDescription (transaction.getRequest().getDescription());
        txnRequest.setOrder (order);

        // Create the payment options
        profile.setCustomerProfileId (transaction.getRequest().getCustomer().getProfileToken());
        txnRequest.setProfile (profile);

        // Create the line items
        txnRequest = createLineItemList (txnRequest, transaction.getRequest());
        apiRequest.setTransactionRequest (txnRequest);

        // Configure hosted page settings
        apiRequest = (GetHostedPaymentPageRequest)configureHostedPaymentSettings (apiRequest, transaction.getRequest().getPaymentSettings(), "payment");

        controller = new GetHostedPaymentPageController (apiRequest);
        controller.execute();

        response = controller.getApiResponse();

        if (response != null)
        {
            transaction.getRequest().getCustomer().setErrorReponse (response);

            if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get (0).getText());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Hosted Page Token - " + response.getToken());

                transaction.getRequest().getCustomer().setToken (response.getToken());
                transaction.getResponse().setErrorCode (TRANSACTION_SUCCESS);
                transaction.getResponse().setErrorMessage (response.getMessages().getMessage().get (0).getText());
           }
           else
           {
                Logger.log (this.getClass(), ANET_PAYMENT_PAGE_LOAD, response.getMessages().getMessage().get (0).getText());

                transaction.getResponse().setErrorMessage (response.getMessages().getMessage().get (0).getText());
                transaction.getResponse().setErrorCode (ANET_PAYMENT_PAGE_LOAD);
           }
        }
        else
        {
            Logger.log (this.getClass(), ANET_PAYMENT_PAGE_LOAD, TRANS_PAGE_LOAD_FAILURE);

            try
            {
                transaction.setRefId (controller.getErrorResponse().getRefId());
                transaction.getResponse().setErrorMessage (controller.getErrorResponse().getMessages().getMessage().get (0).getText());
            }
            catch (NullPointerException e)
            {
                transaction.getResponse().setErrorMessage (TRANS_PAGE_LOAD_FAILURE);
                transaction.getResponse().setErrorCode (TRANSACTION_RESP_UKNWN);
            }
        }

        return transaction;
    }

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

    private TransactionRequestType createLineItemList (TransactionRequestType txnRequest, TransRequest request) throws NumberFormatException
    {
        ArrayOfLineItem lineItems = new ArrayOfLineItem();
        LineItemType lineItem;
        Iterator accountItr = request.getAccountList().iterator();
        Iterator invoiceItr;
        IAccount account;
        IInvoice invoice;

        // Iterate through accounts
        do
        {
            account = (IAccount)accountItr.next();
            invoiceItr = account.getInvoices().iterator();

            do
            {
                invoice = (IInvoice)invoiceItr.next();
                lineItem = new LineItemType();
                lineItem.setItemId (invoice.getInvoiceNumber());
                lineItem.setDescription (invoice.getDescription());
                lineItem.setName ("Statement");

                // May throw exception
                lineItem.setQuantity (BigDecimal.ONE);
                lineItem.setUnitPrice (BigDecimal.valueOf (Double.valueOf (invoice.getOpenAmount())));
                lineItem.setTaxable (Boolean.FALSE);
                lineItems.getLineItem().add (lineItem);
            } while (invoiceItr.hasNext());

        } while (accountItr.hasNext());

        txnRequest.setLineItems (lineItems);
        return txnRequest;
    }

    private TransactionRequestType createTransactionSettings (TransactionRequestType txnRequest, Collection<ITransactionSetting> settingsList)
    {
        ArrayOfSetting settingsArray = new ArrayOfSetting();
        SettingType setting;
        TransactionSetting transSetting = null;
        Iterator settingItr = null;
        boolean valid = true;

        if (settingsList != null)
        {
            settingItr = settingsList.iterator();
        }
        else
        {
            valid = false;
        }

        if (valid)
        {
            do
            {
                transSetting = (TransactionSetting)settingItr.next();
                setting = new SettingType();
                setting.setSettingName (transSetting.getSettingName());
                setting.setSettingValue (transSetting.getSettingValue());
                settingsArray.getSetting().add (setting);
            } while (settingItr.hasNext());

            txnRequest.setTransactionSettings (settingsArray);
        }
        // else do nothing

        return txnRequest;
    }

    private TransactionTypeEnum getTransactionType (int type)
    {
        TransactionTypeEnum result;

        switch (type)
        {
            case TTYPE_AUTH_ONLY:
                result = TransactionTypeEnum.AUTH_ONLY_TRANSACTION;
                break;

            case TTYPE_AUTH_CAPTURE:
                result = TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION;
                break;

            case TTYPE_CAPTURE_ONLY:
                result = TransactionTypeEnum.CAPTURE_ONLY_TRANSACTION;
                break;

            case TTYPE_REFUND:
                result = TransactionTypeEnum.REFUND_TRANSACTION;
                break;

            case TTYPE_PRIOR_AUTH_CAPTURE:
                result = TransactionTypeEnum.PRIOR_AUTH_CAPTURE_TRANSACTION;
                break;

            case TTYPE_VOID:
                result = TransactionTypeEnum.VOID_TRANSACTION;
                break;

            case TTYPE_GET_DETAILS:
                result = TransactionTypeEnum.GET_DETAILS_TRANSACTION;
                break;

            case TTYPE_AUTH_ONLY_CONTINUE:
                result = TransactionTypeEnum.AUTH_ONLY_CONTINUE_TRANSACTION;
                break;

            case TTYPE_AUTH_CAPTURE_CONTINUE:
                result = TransactionTypeEnum.AUTH_CAPTURE_CONTINUE_TRANSACTION;
                break;

            default:
                result = null;
        }

    return result;
}
}
