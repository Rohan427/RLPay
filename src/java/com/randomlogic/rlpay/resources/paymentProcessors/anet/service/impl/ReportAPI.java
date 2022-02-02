/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.InternalPaymentProfile;
import com.randomlogic.rlpay.model.domain.payment.PayTransaction;
import com.randomlogic.rlpay.model.domain.payment.PayTransactionReport;
import com.randomlogic.rlpay.model.domain.payment.PaymentInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.BankAccount;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Batch;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.BillingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.CreditCard;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Duty;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.ReturnedItem;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Shipping;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.ShippingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Tax;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.TransactionDetail;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IBatchStatistic;
import com.randomlogic.rlpay.model.domain.interfaces.ICreditCard;
import com.randomlogic.rlpay.model.domain.interfaces.IDetailedAmount;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IFraudFilter;
import com.randomlogic.rlpay.model.domain.interfaces.IInternalPaymentProfile;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IReturnedItem;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IShippingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ITransactionDetail;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.parms.PaymentParms;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ArrayOfBatchStatisticType;
import net.authorize.api.contract.v1.ArrayOfFDSFilter;
import net.authorize.api.contract.v1.ArrayOfLineItem;
import net.authorize.api.contract.v1.ArrayOfReturnedItem;
import net.authorize.api.contract.v1.BatchDetailsType;
import net.authorize.api.contract.v1.BatchStatisticType;
import net.authorize.api.contract.v1.CustomerTypeEnum;
import net.authorize.api.contract.v1.ExtendedAmountType;
import net.authorize.api.contract.v1.FDSFilterType;
import net.authorize.api.contract.v1.GetTransactionDetailsRequest;
import net.authorize.api.contract.v1.GetTransactionDetailsResponse;
import net.authorize.api.contract.v1.LineItemType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.OrderType;
import net.authorize.api.contract.v1.ReturnedItemType;
import net.authorize.api.contract.v1.TransactionDetailsType;
import net.authorize.api.controller.GetTransactionDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;
import com.randomlogic.rlpay.portal.payment.domain.LineItem;
import com.randomlogic.rlpay.portal.payment.domain.Order;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.ANET_REPORT_FAILED;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.REPORT_DETAILS_FAIL;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.TRANSACTION_SUCCESS;

/**
 *
 * @author Paul.Allen
 */
public class ReportAPI
{
    /** Customer profile data */
    private CustomerProfile customer = null;
    private String errorMessage;
    private ICreditCard creditcard;
    private InternalPaymentProfile paymentProfile;
    private Collection<IInternalPaymentProfile> paymentList;
    private BillingInfo billing;
    private IPayTransactionReport report = new PayTransactionReport();
    private BankAccount bankaccount;
    private IShippingInfo shippingInfo;

    public IPayTransactionReport getSettledBatchList (PaymentParms payParms)
    {
        IPayTransactionReport report = new PayTransactionReport();



        return report;
    }

    public IPayTransactionReport getTransactionDetails (PaymentParms payParms)
    {
        GetTransactionDetailsRequest getRequest = new GetTransactionDetailsRequest();
        GetTransactionDetailsController controller;
        ITransactionDetail transaction = new TransactionDetail();
        Collection<IFraudFilter> fdsFilters = null;
        Order order;
        Batch batch;
        IDetailedAmount tax;
        IDetailedAmount duty;
        IDetailedAmount shipping;
        PayTransaction payTrans;
        Collection<ITransactionDetail> transactionList = new ArrayList<ITransactionDetail>();
        Logs log = new Logs();

        log.setMethod ("report");
        log.setCommand ("details");
        log.setErrorSource (this.getClass().toString());

        ApiOperationBase.setMerchantAuthentication (payParms.getEnvironment());

        getRequest.setMerchantAuthentication (payParms.getEnvironment());
        getRequest.setTransId (payParms.getTransaction().getRequest().getReportReq().getTransactionId());

        controller = new GetTransactionDetailsController (getRequest);
        controller.execute();

        GetTransactionDetailsResponse response = controller.getApiResponse();

        if (response != null)
        {
            report.setRefId (response.getRefId());

            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                TransactionDetailsType details = response.getTransaction();

                Logger.log (this.getClass(), TRANSACTION_SUCCESS, response.getMessages().getMessage().get (0).getText());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Transaction ID: " + details.getTransId());

                report.setErrorCode (TRANSACTION_SUCCESS);
                report.setErrorMessage (response.getMessages().getMessage().get (0).getText());
                report.setResponseCode (response.getMessages().getResultCode().value());

                payTrans = getTransaction (details);
                transaction.setTransaction (payTrans);

                log.setTransactionId (payTrans.getTransId());
                log.setAuthCode (payTrans.getAuthCode());
                log.setAmount (transaction.getAuthAmount());

                transaction.setfDSFilterAction (details.getFDSFilterAction());

                fdsFilters = getFdsFilters (details);
                transaction.setfDSFilters (fdsFilters);

                order = getOrder (details);
                transaction.setOrder (order);

                if (details.getRequestedAmount() != null)
                {
                    transaction.setRequestedAmount (details.getRequestedAmount().toString());
                }
                // else do nothing

                transaction.setAuthAmount (details.getAuthAmount().toString());
                transaction.setSettleAmount (details.getSettleAmount().toString());

                batch = getBatch (details);
                transaction.setBatch (batch);

                log.setBatchId (transaction.getBatch().getBatchId());

                tax = getTax (details);
                transaction.setTax ((Tax)tax);

                duty = getDuty (details);
                transaction.setDuty ((Duty)duty);

                shipping = getShipping (details);
                transaction.setShipping ((Shipping)shipping);

                transaction.setLineItems (getLineItems (details));

                if (details.getPrepaidBalanceRemaining() != null)
                {
                    transaction.setPrepaidBalanceRemaining (details.getPrepaidBalanceRemaining().toString());
                }
                // else do nothing

                createBilling (details);
                createBankAccount (details);
                createCreditCard (details);
                createShippingInfo (details);
                createPayments (details);
                createCustomer (details);
                customer.setErrorReponse (response);
                transaction.setCustomer (customer);

                log.setCustomerId (transaction.getCustomer().getCustomerId());

                transaction.getTax().setTaxExempt (details.isTaxExempt());
                transaction.setShippingInfo (shippingInfo);
                transaction.setRecurringBilling (details.isRecurringBilling());
                transaction.setReturnedItemList (getReturnedItems (details));

                if (details.getSolution() != null)
                {
                    transaction.getSolution().setId (details.getSolution().getId());
                    transaction.getSolution().setName (details.getSolution().getName());
                    transaction.getSolution().setVendorName (details.getSolution().getVendorName());
                }
                //else do nothing

                transaction.setCustomerIP (details.getCustomerIP());

                if (details.getSubscription() != null)
                {
                    transaction.getSubscription().setId (details.getSubscription().getId());
                    transaction.getSubscription().setPayNum (details.getSubscription().getPayNum());
                }
                //else do nothing

                transaction.setMarketType (details.getMarketType());
                transaction.setProduct (details.getProduct());
                transaction.setMobileDeviceId (details.getMobileDeviceId());
////                transaction.setRefId (details.getRefTransId());

                transactionList.add (transaction);
                report.setTransactions (transactionList);

                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Successfully retrieved transaction with Transaction ID: " + details.getTransId());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Response Code: " + details.getResponseCode());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Description: " + details.getResponseReasonDescription());
                Logger.log (this.getClass(), TRANSACTION_SUCCESS, "Auth Code: " + details.getAuthCode());
            }
            else
            {
                report.setErrorCode (ANET_REPORT_FAILED);
                report.setErrorMessage (response.getMessages().getResultCode() + ": " +
                                        response.getMessages().getMessage().get (0).getText());
                report.setDescription (REPORT_DETAILS_FAIL);


                Logger.log (this.getClass(), "Error Code: " + response.getMessages().getResultCode());
                Logger.log (this.getClass(), "Error message: " + response.getMessages().getMessage().get (0).getText());
            }
        }
        else
        {
            report.setRefId (payParms.getTransaction().getRefId());

            // Display the error code and message when response is null
            ANetApiResponse errorResponse = controller.getErrorResponse();
            Logger.log (this.getClass(), "Failed to get response");

            if (!errorResponse.getMessages().getMessage().isEmpty())
            {
                report.setErrorCode (ANET_REPORT_FAILED);
                report.setErrorMessage (errorResponse.getMessages().getMessage().get (0).getCode() + ": " +
                                        errorResponse.getMessages().getMessage().get (0).getText()
                                       );

                Logger.log (this.getClass(),
                            "Error: " + errorResponse.getMessages().getMessage().get (0).getCode() +
                            "\n" + errorResponse.getMessages().getMessage().get (0).getText()
                           );
            }
            else
            {
                report.setErrorCode (ANET_REPORT_FAILED);
                report.setErrorMessage ("Transaction Report Processor failed to send response");
            }
        }

        log.setErrorCode (report.getMessageCode());
        log.setErrorMsg (report.getErrorMessage());
        log.setLogText (report.getDescription());

        Logger.log (Logger.ERROR, this.getClass(), log, true);

        return report;
    }

    public IPayTransactionReport getTransactionList (PaymentParms payParms)
    {
        IPayTransactionReport report = new PayTransactionReport();



        return report;
    }

    public IPayTransactionReport getUnsettledTransactionList (PaymentParms payParms)
    {
        IPayTransactionReport report = new PayTransactionReport();



        return report;
    }

    private Collection<IFraudFilter> getFdsFilters (TransactionDetailsType details)
    {
        List<IFraudFilter> fdsFilter = new ArrayList<IFraudFilter>();
        ArrayOfFDSFilter filterArray;
        List<FDSFilterType> filterList;
        Iterator filterItr;
        FDSFilterType filter;

        filterArray = details.getFDSFilters();

        if (filterArray != null)
        {
            filterList = filterArray.getFDSFilter();
            filterItr = filterList.iterator();

            do
            {
                filter = (FDSFilterType)filterItr.next();
                fdsFilter.add ((IFraudFilter)filter);
            } while (filterItr.hasNext());
        }
        // else do nothing

        return fdsFilter;
    }

    private Order getOrder (TransactionDetailsType details)
    {
        Order order = new Order();
        OrderType detailOrder = details.getOrder();

        order.setInvoiceNumber (detailOrder.getInvoiceNumber());
        order.setDescription (detailOrder.getDescription());
        order.setPurchaseOrderNo ("N/A");

        return order;
    }

    private Batch getBatch (TransactionDetailsType details)
    {
        Batch batch = new Batch();
        BatchDetailsType detailBatch = details.getBatch();
        ArrayOfBatchStatisticType statArray;
        List<BatchStatisticType> batchStats;
        IBatchStatistic batchStat;
        List<IBatchStatistic> statList = new ArrayList<IBatchStatistic>();
        Iterator statItr;

        batch.setBatchId (detailBatch.getBatchId());
        batch.setSettlementTimeUTC (detailBatch.getSettlementTimeUTC().toGregorianCalendar().getTime());
        batch.setSettlementLocalTime (detailBatch.getSettlementTimeLocal().toGregorianCalendar().getTime());
        batch.setSettlementState (detailBatch.getSettlementState());
        batch.setPaymentMethod (detailBatch.getProduct());
        batch.setMarketType (detailBatch.getMarketType());
        batch.setCardPresent (detailBatch.getProduct());

        statArray = detailBatch.getStatistics();

        if (statArray != null)
        {
            batchStats = statArray.getStatistic();
            statItr = batchStats.iterator();

            do
            {
                batchStat = (IBatchStatistic)statItr.next();
                statList.add (batchStat);
            } while (statItr.hasNext());

            batch.setStatsList (statList);
        }
        // else do nothing

        return batch;
    }

    private PayTransaction getTransaction (TransactionDetailsType details)
    {
        PayTransaction transaction = new PayTransaction();

        transaction.setTransId (details.getTransId());
        transaction.setRefTransId (details.getRefTransId());
        transaction.setSplitTenderId (details.getSplitTenderId());
        transaction.setSubmitTimeUTC (details.getSubmitTimeUTC().toGregorianCalendar().getTime());
        transaction.setSubmitTimeLocal (details.getSubmitTimeLocal().toGregorianCalendar().getTime());
        transaction.setTransType (details.getTransactionType());
        transaction.setTransStatus (details.getTransactionStatus());
        transaction.setResponseCode (details.getResponseCode());
        transaction.setResponseReasonCode (details.getResponseReasonCode());
        transaction.setResponseReasonDescription (details.getResponseReasonDescription());
        transaction.setAuthCode (details.getAuthCode());
        transaction.setAVSResponse (details.getAVSResponse());
        transaction.setCardCodeResponse (details.getCardCodeResponse());
        transaction.setCAVVResponse (details.getCAVVResponse());

        return transaction;
    }

    private IDetailedAmount getTax (TransactionDetailsType details)
    {
        Tax tax = new Tax();
        ExtendedAmountType detailTax = details.getTax();

        if (detailTax != null)
        {
            tax.setAmount (detailTax.getAmount());
            tax.setDescription (detailTax.getDescription());
            tax.setName (detailTax.getName());
        }
        // else do nothing

        return tax;
    }

    private IDetailedAmount getDuty (TransactionDetailsType details)
    {
        Duty duty = new Duty();
        ExtendedAmountType detailDuty = details.getDuty();

        if (detailDuty != null)
        {
            duty.setAmount (detailDuty.getAmount());
            duty.setDescription (detailDuty.getDescription());
            duty.setName (detailDuty.getName());
        }
        //else do nothing

        return duty;
    }

    private IDetailedAmount getShipping (TransactionDetailsType details)
    {
        Shipping shipping = new Shipping();
        ExtendedAmountType detailShipping = details.getShipping();

        if (detailShipping != null)
        {
            shipping.setAmount (detailShipping.getAmount());
            shipping.setDescription (detailShipping.getDescription());
            shipping.setName (detailShipping.getName());
        }
        // else do nothing

        return shipping;
    }

    private Collection<LineItem> getLineItems (TransactionDetailsType details)
    {
        List<LineItem> itemList = new ArrayList<LineItem>();
        ArrayOfLineItem itemArray = details.getLineItems();
        List<LineItemType> detailList;
        LineItemType detailItem;
        LineItem item;
        Iterator itemItr;

        if (itemArray != null)
        {
            detailList = itemArray.getLineItem();
            itemItr = detailList.iterator();

            do
            {
                detailItem = (LineItemType)itemItr.next();
                item = new LineItem();

                item.setItemNo (detailItem.getItemId());
                item.setDescription (detailItem.getDescription());
                item.setName (detailItem.getName());
                item.setQty (detailItem.getQuantity().intValue());
                item.setOpenAmount (detailItem.getUnitPrice().toString());
                item.setTaxable (detailItem.isTaxable());

                itemList.add (item);
            } while (itemItr.hasNext());
        }
        //else do nothing;

        return itemList;
    }

    private Collection<IReturnedItem> getReturnedItems (TransactionDetailsType details)
    {
        ArrayList<IReturnedItem> itemList = new ArrayList<IReturnedItem>();
        ArrayOfReturnedItem itemArray = details.getReturnedItems();
        List<ReturnedItemType> detailList;
        ReturnedItemType detailItem;
        IReturnedItem item;
        Iterator itemItr;

        if (itemArray != null)
        {
            detailList = itemArray.getReturnedItem ();
            itemItr = detailList.iterator();

            do
            {
                detailItem = (ReturnedItemType)itemItr.next();
                item = new ReturnedItem();

                item.setId (detailItem.getId());
                item.setAchCode (detailItem.getCode());
                item.setDateLocal (detailItem.getDateLocal().toGregorianCalendar().getTime());
                item.setDateUTC (detailItem.getDateUTC().toGregorianCalendar().getTime());
                item.setDesc (detailItem.getDescription());

                itemList.add (item);
            } while (itemItr.hasNext());
        }
        //else do nothing;

        return itemList;
    }

    private void createCustomer (TransactionDetailsType details)
    {
        customer = new CustomerProfile();

        customer.setCustomerId (details.getCustomer().getId());
        customer.setEmail (details.getCustomer().getEmail());
        customer.setPaymentProfiles (paymentList);

        if (details.getProfile() != null)
        {
            customer.setProfileToken (details.getProfile().getCustomerProfileId());
        }
        //else do nothing

        customer.setProfileType (convertCustomerType (details.getCustomer().getType()));
    }

    private void createPayments (TransactionDetailsType details)
    {
        PaymentInfo payment = new PaymentInfo();

        paymentList = new ArrayList<IInternalPaymentProfile>();
        payment.setCreditcard (creditcard);
        payment.setBankaccount (bankaccount);

        paymentProfile = new InternalPaymentProfile();
        paymentProfile.setBilling (billing);
        paymentProfile.setPayment (payment);

        if (details.getProfile() != null)
        {
            paymentProfile.setProfileId (details.getProfile().getCustomerPaymentProfileId());
        }
        //else do nothing

        paymentList.add (paymentProfile);
    }

    private void createBilling (TransactionDetailsType details)
    {
        billing = new BillingInfo();

        billing.setAddress (details.getBillTo().getAddress());
        billing.setCity (details.getBillTo().getCity());
        billing.setCompany (details.getBillTo().getCompany());
        billing.setCountry (details.getBillTo().getCountry());
        billing.setFaxNumber (details.getBillTo().getFaxNumber());
        billing.setFirstName (details.getBillTo().getFirstName());
        billing.setLastName (details.getBillTo().getLastName());
        billing.setPhoneNumber (details.getBillTo().getPhoneNumber());
        billing.setState (details.getBillTo().getState());
        billing.setZip (details.getBillTo().getZip());
    }

    private void createBankAccount (TransactionDetailsType details)
    {
        bankaccount = new BankAccount();

        if (details.getPayment().getBankAccount() != null)
        {
            bankaccount.setAccountNumber (details.getPayment().getBankAccount().getAccountNumber());
            bankaccount.setAccountType (details.getPayment().getBankAccount().getAccountType().value());
            bankaccount.setBankName (details.getPayment().getBankAccount().getBankName());
            bankaccount.setNameOnAccount (details.getPayment().getBankAccount().getNameOnAccount());
            bankaccount.setRoutingNumber (details.getPayment().getBankAccount().getRoutingNumber());
            bankaccount.setEcheckType (details.getPayment().getBankAccount().getEcheckType().value());
        }
        // else do nothing
    }

    private void createCreditCard (TransactionDetailsType details)
    {
        creditcard = new CreditCard();

        if (details.getPayment().getCreditCard() != null)
        {
            creditcard.setCardNumber (details.getPayment().getCreditCard().getCardNumber());
            creditcard.setCardType (details.getPayment().getCreditCard().getCardType());
            creditcard.setExpirationDate (details.getPayment().getCreditCard().getExpirationDate());
        }
        // else do nothing
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

    private void createShippingInfo (TransactionDetailsType details)
    {
        shippingInfo = new ShippingInfo();

        if (details.getShipTo() != null)
        {
            shippingInfo.setAddress (details.getShipTo().getAddress());
            shippingInfo.setCity (details.getShipTo().getCity());
            shippingInfo.setCompany (details.getShipTo().getCompany());
            shippingInfo.setCountry (details.getShipTo().getCountry());
            shippingInfo.setFirstName (details.getShipTo().getFirstName());
            shippingInfo.setLastName (details.getShipTo().getLastName());
            shippingInfo.setState (details.getShipTo().getState());
        }
        //else no nohting
    }
}
