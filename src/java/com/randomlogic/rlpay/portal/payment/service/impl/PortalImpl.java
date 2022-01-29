/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.MessageContext;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.IAccount;
import com.randomlogic.rlpay.model.domain.interfaces.IInvoice;
import com.randomlogic.rlpay.model.domain.payment.Account;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.Invoice;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.model.service.interfaces.Ipersist;
import com.randomlogic.rlpay.portal.payment.domain.ClientRequest;
import com.randomlogic.rlpay.portal.payment.domain.ClientResponse;
import com.randomlogic.rlpay.portal.payment.domain.LineItem;
import com.randomlogic.rlpay.portal.payment.domain.LogSearchRequest;
import com.randomlogic.rlpay.portal.payment.domain.LogSearchResponse;
import com.randomlogic.rlpay.portal.payment.domain.PaymentRequest;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;
import com.randomlogic.rlpay.portal.payment.domain.ReportRequest;
import com.randomlogic.rlpay.portal.payment.domain.ReportResponse;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Controller
public class PortalImpl
{
    private static final int DEBUG = Logger.MOD;

    @Autowired
    public PortalMethodImpl methods;

    private ServletParams params;
    private ServletContext context;
    private MessageContext msgContext;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Client client = null;

    public PortalImpl()
    {

    }

    public void initContext (MessageContext msgContext)
    {
        this.msgContext = msgContext;
        context = (ServletContext) msgContext.get (MessageContext.SERVLET_CONTEXT);
        request = (HttpServletRequest) msgContext.get (MessageContext.SERVLET_REQUEST);
        response = (HttpServletResponse) msgContext.get (MessageContext.SERVLET_RESPONSE);

        // Add CORS header
        response.setHeader ("Access-Control-Allow-Origin", "*");

        params = new ServletParams (new ErrorBean(), context, request, response);
    }

    public void setMethods (PortalMethodImpl methods)
    {
        this.methods = methods;
    }

    public ClientResponse register (ClientRequest request, int environment)
    {
        String url;
        ClientResponse lresponse = new ClientResponse();
        TransactionRecord transaction = new TransactionRecord();

        client = request.getClient();
        methods.setEnvironment (environment);

        if (request.getGuid() != null)
        {
            if (!request.getGuid().equals (""))
            {
                transaction.getRequest().setCustomer (new CustomerProfile());
                transaction.getRequest().getCustomer().setCustomerId (request.getCustAccount());
                transaction.getRequest().getCustomer().setEmail (request.getEmail());
                transaction.getRequest().getCustomer().setDescription (request.getDescription());
                transaction.getRequest().setClient (client);
                transaction.setRefId (request.getGuid());
                transaction.setMode ("profile");
                transaction.setCommand ("register");

                transaction = methods.requestTransaction (transaction, params);
                client = (Client)transaction.getRequest().getClient();
                params.setClient (client);
                url = params.getCfg().getTestManage();

                if (!client.getAuthToken().equals ("0"))
                {
                    if (transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                        && transaction.getRefId().equals (request.getGuid())
                       )
                    {
                        lresponse.setHostedUrl (url);
                        lresponse.setGuid (transaction.getRefId());
                        lresponse.setCustAccount (transaction.getRequest().getCustomer().getCustomerId());
                        lresponse.setProfileToken (transaction.getRequest().getCustomer().getProfileToken());
                        lresponse.setClient (client);
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        lresponse.setClient (client);

                        if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                        {
                            lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                            lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                        }
                        else
                        {
                            lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                            lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                        }
                    }
                }// ENDIF: if (!client.getAuthToken().equals ("0"))
                else
                {
                    lresponse.setErrorMsg (TRANS_NOT_AUTH);
                    lresponse.setErrorCode ("" + TRANSACTION_NOAUTH);
                    lresponse.setTransactionId ("0");
                    lresponse.setGuid (request.getGuid());
                }
            }
            else
            {
                lresponse.setErrorMsg (TRANS_NO_GUID);
                lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
            }
        }// ENDIF: if (request.getGuid() != null)
        else
        {
            lresponse.setErrorMsg (TRANS_NO_GUID);
            lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        lresponse.setClient (client);
        return lresponse;
    }

    public ClientResponse login (ClientRequest request, int environment)
    {
        String url;
        ClientResponse lresponse = new ClientResponse();
        TransactionRecord transaction = new TransactionRecord();

        client = request.getClient();
        methods.setEnvironment (environment);

        if (request.getGuid() != null)
        {
            if (!request.getGuid().equals (""))
            {
                transaction.getRequest().setCustomer (new CustomerProfile());
                transaction.getRequest().getCustomer().setCustomerId (request.getCustAccount());
                transaction.getRequest().getCustomer().setEmail (request.getEmail());
                transaction.setRefId (request.getGuid());
                transaction.getRequest().setClient (client);
                transaction.getRequest().setPaymentSettings (request.getHostedPageSettings());
                transaction.setMode ("profile");
                transaction.setCommand ("login");

                transaction = methods.requestTransaction (transaction, params);
                url = params.getCfg().getTestManage();

                client = (Client)transaction.getRequest().getClient();
                params.setClient (client);

                if (!client.getAuthToken().equals ("0"))
                {
                    if (transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                       && transaction.getRefId().equals (request.getGuid())
                       )
                    {
                        lresponse.setHostedUrl(url);
                        lresponse.setGuid (transaction.getRefId());
                        lresponse.setClient (client);
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());

                        if (transaction.getRequest().getCustomer() != null)
                        {
                            lresponse.setCustAccount (transaction.getRequest().getCustomer().getCustomerId());
                            lresponse.setProfileToken (transaction.getRequest().getCustomer().getProfileToken());
                            lresponse.setHostedToken (transaction.getRequest().getCustomer().getToken());
                        }
                        // else do nothing
                    }
                    else
                    {
                        lresponse.setClient (client);

                        if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                        {
                            lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                            lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                        }
                        else
                        {
                            lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                            lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                        }
                    }
                }// ENDIF: if (!client.getAuthToken().equals ("0"))
                else
                {
                    lresponse.setErrorMsg (TRANS_NOT_AUTH);
                    lresponse.setErrorCode ("" + TRANSACTION_NOAUTH);
                    lresponse.setTransactionId ("0");
                    lresponse.setGuid (request.getGuid());
                }
            }
            else
            {
                lresponse.setErrorMsg (TRANS_NO_GUID);
                lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
            }
        }// ENDIF: if (request.getGuid() != null)
        else
        {
            lresponse.setErrorMsg (TRANS_NO_GUID);
            lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        lresponse.setClient (client);
        return lresponse;
    }

    public ClientResponse manage (ClientRequest request, int environment)
    {
        String url;
        ClientResponse lresponse = new ClientResponse();
        TransactionRecord transaction = new TransactionRecord();

        client = request.getClient();
        methods.setEnvironment (environment);

        if (request.getGuid() != null)
        {
            transaction.getRequest().setCustomer (new CustomerProfile());
            transaction.getRequest().getCustomer().setCustomerId (request.getCustAccount());
            transaction.getRequest().getCustomer().setEmail (request.getEmail());
            transaction.setRefId (request.getGuid());
            transaction.getRequest().setClient (client);
            transaction.getRequest().setPaymentSettings (request.getHostedPageSettings());
            transaction.setMode ("profile");
            transaction.setCommand ("manage");

            transaction = methods.requestTransaction (transaction, params);
            url = params.getCfg().getTestManage();

            // Send the profile manage page request
            client = (Client)transaction.getRequest().getClient();

            lresponse.setGuid (transaction.getRefId());

            if (!client.getAuthToken().equals ("0"))
            {
                if (transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                   && transaction.getRefId().equals (request.getGuid())
                   )
                {
                    lresponse.setHostedUrl(url);
                    lresponse.setClient (client);
                    lresponse.setHostedToken (transaction.getRequest().getCustomer().getToken());
                    lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                    lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());

                    if (transaction.getRequest().getCustomer() != null)
                    {
                        lresponse.setCustAccount (transaction.getRequest().getCustomer().getCustomerId());
                        lresponse.setProfileToken (transaction.getRequest().getCustomer().getProfileToken());
                        lresponse.setHostedToken (transaction.getRequest().getCustomer().getToken());
                    }
                    // else do nothing
                }
                else
                {
                    lresponse.setClient (client);

                    if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                    {
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                    }
                }
            }// ENDIF: if (!client.getAuthToken().equals ("0"))
            else
            {
                lresponse.setErrorMsg (TRANS_NOT_AUTH);
                lresponse.setErrorCode ("" + TRANSACTION_NOAUTH);
                lresponse.setTransactionId ("0");
                lresponse.setGuid (request.getGuid());
            }
        }// ENDIF: if (request.getGuid() != null)
        else
        {
            lresponse.setErrorMsg (TRANS_NO_GUID);
            lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        lresponse.setClient (client);
        return lresponse;
    }

    public ClientResponse payment (PaymentRequest request, int environment)
    {
        String url;
        ClientResponse lresponse = new ClientResponse();
        TransactionRecord transaction = new TransactionRecord();
        client = request.getClientData().getClient();
        Collection<IAccount> accounts = new ArrayList<IAccount>();
        Collection<IInvoice> invoices = new ArrayList<IInvoice>();
        Iterator<LineItem> itemItr;
        IAccount account = new Account();
        IInvoice invoice;
        LineItem lineItem;
        Calendar today = Calendar.getInstance();

        url = params.getCfg().getTestPayment();
        methods.setEnvironment (environment);

        if (request.getClientData().getGuid() != null)
        {
            transaction.getRequest().setCustomer (new CustomerProfile());
            transaction.getRequest().getCustomer().setCustomerId (request.getClientData().getCustAccount());
            transaction.getRequest().getCustomer().setEmail (request.getClientData().getEmail());
            transaction.getRequest().setClient (client);

            if (null != request.getPaymentNonce())
            {
                lresponse = noncePayment (transaction, request);
            }
            else
            {
                transaction.setMode ("profile");
                transaction.setCommand ("load");

                transaction.setRefId (request.getClientData().getGuid());
                transaction = methods.requestTransaction (transaction, params);
                client = (Client)transaction.getRequest().getClient();

                if (transaction.getRefId().equals (request.getClientData().getGuid())
                    && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                   )
                {
                    itemItr = request.getAccount().getItems().iterator();

                    /*
                    * Do to limitations on Phase I implementation, we can't get the
                    * item list for reconciliation. So, we need to grade some data
                    * from the first item in the list and pop it into another API
                    * field.
                    */
                    int i = 0;

                    // Get list of invoices
                    do
                    {
                        lineItem = itemItr.next();

                        // PHASE I BEGIN
                        if (i < 1)
                        {
                            transaction.getRequest().setDescription (lineItem.getDescription());
                        }
                        // else do nothing

                        // PHASE I END

                        invoice = convertLineItemToInvoice (lineItem);
                        invoices.add (invoice);
                    } while (itemItr.hasNext());

                    // populate the account and add it to the account list
                    account.setAccountNo (request.getAccount().getAccountNo());
                    account.setStatementNo (request.getAccount().getStatementNo());
                    account.setInvoices (invoices);
                    accounts.add (account);

                    transaction.getRequest().setAmount (Double.valueOf (request.getAccount().getAmount()));

                    Logger.debug (PortalImpl.class, DEBUG, "\n\nAMOUNT: " + request.getAccount().getAmount() +  ", " + transaction.getRequest().getAmount());

                    transaction.setRefId (request.getClientData().getGuid());
                    transaction.getRequest().setAccountList (accounts);
                    transaction.getRequest().setTransInvoiceNo (request.getAccount().getStatementNo());

//// IMPLEMENT FOR PHASE II
////                    transaction.getRequest().setDescription ("Payment for account " + account.getAccountNo()
////                                                             + ", Date: " + today.getTime()
////                                                             + ", Statement: " + request.getAccount().getStatementNo()
////                                                            );
//// PHASE II END

                    transaction.getRequest().setPaymentSettings (request.getClientData().getHostedPageSettings());

                    transaction.setMode ("payment");
                    transaction.setCommand ("payment");
                    transaction.getRequest().setTransactionType (IPaymentSvc.TTYPE_AUTH_CAPTURE);

                    // Send the payment transaction request
                    transaction = methods.requestTransaction (transaction, params);

                    lresponse.setGuid (transaction.getRefId());

                    if (transaction.getRefId().equals (request.getClientData().getGuid())
                        && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS)
                    {
                        lresponse.setHostedUrl(url);
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());

                        if (transaction.getRequest().getCustomer() != null)
                        {
                            lresponse.setCustAccount (transaction.getRequest().getUser().getAccountNum());
                            lresponse.setProfileToken (transaction.getRequest().getCustomer().getProfileToken());
                            lresponse.setHostedToken (transaction.getRequest().getCustomer().getToken());
                        }
                    }
                    else
                    {
                        if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                        {
                            lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                            lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                        }
                        else
                        {
                            lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                            lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                        }
                    }
                } // ENDIF: if (transaction.getRefId().equals...
                else
                {
                    if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                    {
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                    }
                }
            }// END IF_ELSE: if (null != request.getPaymentNonce())
        }// ENDIF: if (request.getClientData().getGuid() != null)
        else
        {
            lresponse.setErrorMsg (TRANS_NO_GUID);
            lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        lresponse.setClient (client);
        return lresponse;
    }

    public LogSearchResponse log (LogSearchRequest request, int environment)
    {
        LogSearchResponse lresponse = new LogSearchResponse();
        TransactionRecord transaction = new TransactionRecord();

        client = request.getClient();
        methods.setEnvironment (environment);

       // Require an account number or do nothing
       // This is for security for the open nature of the PortalMethodImpl server
////       if (request.getLog().getCustomerId() != null || !request.getLog().getCustomerId().equals (""))
////       {
////            if (request.getGuid() != null)
////            {
////                transaction.setMode ("profile");
////                transaction.setCommand ("verify");
////                transaction.getRequest().setClient (client);
////
////                transaction.setRefId (request.getGuid());
////                transaction = sandbox.requestTransaction (transaction, params);
////                client = (Client)transaction.getRequest().getClient();
////                lresponse.setClient (client);
////
////                if (transaction.getRefId().equals (request.getGuid())
////                    && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
////                   )
////                {
////
////                    if (DEBUG)
////                    {
////                        System.out.println ("\n\nLog Search: " + request.getLog().getCustomerId() +  ", " + request.getBeginDate());
////                    }
////
////                    // Send the log search request
////                    lresponse.setLogList (sandbox.requestLogs (request, params));
////                    lresponse.setGuid (request.getGuid());
////
////                    if (lresponse.getGuid().equals (request.getGuid()))
////                    {
////                        // do nothing
////                    }
////                    else
////                    {
////                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
////                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
////                        lresponse.setLogList (new ArrayList<Logs>());
////                    }
////                } // ENDIF: if (transaction.getRefId().equals (request.getGuid())...
////                else
////                {
////                    if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
////                    {
////                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
////                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
////                        lresponse.setClient (new Client());
////                    }
////                    else
////                    {
////                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
////                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
////                        lresponse.setLogList (new ArrayList<Logs>());
////                        lresponse.setClient (new Client());
////                    }
////                }
////            }// ENDIF: if (request.getGuid() != null)
////            else
////            {
////                lresponse.setErrorMsg (TRANS_NO_GUID);
////                lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
////                lresponse.setLogList (new ArrayList<Logs>());
////            }
////       }// ENDIF: if (request.getLog().getCustomerId() != null ...
////       else
////       {
////            lresponse.setErrorMsg (TRANS_MSG_SUCCESS);
////            lresponse.setErrorCode ("" + TRANSACTION_SUCCESS);
////            lresponse.setLogList (new ArrayList<Logs>());
////       }
////
////        lresponse.setClient (client);
        return lresponse;
    }

    public ClientResponse deleteProfile (ClientRequest request, int environment)
    {
        ClientResponse lresponse = new ClientResponse();
        TransactionRecord transaction = new TransactionRecord();

        client = request.getClient();
        methods.setEnvironment (environment);

        if (request.getGuid() != null)
        {
            transaction.getRequest().setCustomer (new CustomerProfile());
            transaction.getRequest().getCustomer().setCustomerId (request.getCustAccount());
            transaction.getRequest().getCustomer().setEmail (request.getEmail());
            transaction.getRequest().setClient (client);
            transaction.setMode ("profile");
            transaction.setCommand ("load");
            transaction.setRefId (request.getGuid());

            transaction = methods.requestTransaction (transaction, params);
            client = (Client)transaction.getRequest().getClient();
            params.setClient (client);

            lresponse.setGuid (transaction.getRefId());

            if (!client.getAuthToken().equals ("0"))
            {
                if (transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                    && transaction.getRefId().equals (request.getGuid())
                   )
                {
                    transaction.setMode ("profile");
                    transaction.setCommand ("delete");
                    transaction = methods.requestTransaction (transaction, params);

                    if (transaction.getRefId().equals (request.getGuid())
                        && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS
                       )
                    {
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                        {
                            lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                            lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                        }
                        else
                        {
                            lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                            lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                        }
                    }
                }
                else
                {
                    if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                    {
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                    }
                }
            }// ENDIF: if (!client.getAuthToken().equals ("0"))
            else
            {
                lresponse.setErrorMsg (TRANS_NOT_AUTH);
                lresponse.setErrorCode ("" + TRANSACTION_NOAUTH);
                lresponse.setTransactionId ("0");
                lresponse.setGuid (request.getGuid());
            }
        }// ENDIF: if (request.getGuid() != null)
        else
        {
            lresponse.setErrorMsg (TRANS_NO_GUID);
            lresponse.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        lresponse.setClient (client);
        return lresponse;
    }

    public ReportResponse report (ReportRequest request, int environment)
    {
        ReportResponse report = new ReportResponse();
        TransactionRecord transaction = new TransactionRecord();
        Logs log = new Logs();

        log.setErrorSource (this.getClass().toString());
        log.setMethod ("report");
        log.setCommand (request.getCommand());

        client = request.getClient();
        methods.setEnvironment (environment);

        if (request.getGuid() != null)
        {
            transaction.getRequest().setReportReq (request);

            transaction.setMode ("records");
            transaction.setCommand (request.getCommand());

            transaction = methods.requestTransaction (transaction, params);
            report.setGuid (transaction.getRefId());
            log.setGuid (report.getGuid());

            if (transaction.getRequest().getReportReq().getGuid().equals (request.getGuid())
                && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS)
            {
                report.setTransactionReport (transaction.getResponse().getTransactionreport());
                report.setErrorMsg (transaction.getResponse().getErrorMessage());
                report.setErrorCode ("" + transaction.getResponse().getErrorCode());
            }
            else
            {
                if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                {
                    report.setErrorMsg (transaction.getResponse().getErrorMessage());
                    report.setErrorCode ("" + transaction.getResponse().getErrorCode());
                }
                else
                {
                    report.setErrorMsg (TRANS_GUID_MISMATCH);
                    report.setErrorCode ("" + TRANSACTION_GUID_ERR);
                }
            }
        }
        else
        {
            report.setErrorMsg (TRANS_NO_GUID);
            report.setErrorCode ("" + TRANSACTION_NOGUID);
        }

        report.setClient (client);

        return report;
    }

    public String test (ClientRequest request)
    {
        Configuration config = new Configuration();
        Ipersist configDao = new ConfigAccessSvcImpl();

        config.getId().setConfigKey ("testKey");
        config = (Configuration)configDao.find (config);

        if (config.getValue().equals ("testKey"))
        {
            return "OK";
        }
        else
        {
            return "ERROR";
        }
    }

    private IInvoice convertLineItemToInvoice (LineItem item)
    {
        IInvoice invoice = new Invoice();

        invoice.setCustomerPO (item.getCustomerPo());
        invoice.setDescription (item.getDescription());
        invoice.setDueDate (item.getDueDate());
        invoice.setInvoiceDate (item.getInvoiceDate());
        invoice.setInvoiceNumber (item.getItemNo());
        invoice.setOpenAmount (item.getOpenAmount());
        invoice.setOrderNumber (item.getOrderNo());
        invoice.setOrginalAmount (item.getOriginalAmount());

        return invoice;
    }

    private ClientResponse noncePayment (TransactionRecord transaction, PaymentRequest request)
    {
        ClientResponse lresponse = new ClientResponse();
        Collection<IAccount> accounts = new ArrayList<IAccount>();
        Collection<IInvoice> invoices = new ArrayList<IInvoice>();
        Iterator<LineItem> itemItr;
        IAccount account = new Account();
        IInvoice invoice;
        LineItem lineItem;
        Calendar today = Calendar.getInstance();
        Logs log = new Logs();

        log.setErrorSource (this.getClass().toString());
        log.setMethod ("payment");
        log.setCommand ("noncepayment");

        // Clear session client data
        params.setClient (null);

        transaction.setRefId (request.getClientData().getGuid());
        transaction.setMode ("profile");
        transaction.setCommand ("verify");

        transaction = methods.requestTransaction (transaction, params);
        client = (Client)transaction.getRequest().getClient();

        if (transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS)
        {
            transaction.setMode ("payment");
            transaction.setCommand ("noncepayment");
            transaction.getRequest().setTransactionType (IPaymentSvc.TTYPE_AUTH_CAPTURE);

            try
            {
                transaction.getRequest().setAmount (Double.valueOf (request.getAccount().getAmount()));
                transaction.getRequest().getNonce().setDescription (request.getPaymentNonce().getDescription());
                transaction.getRequest().getNonce().setNonce (request.getPaymentNonce().getNonce());

                itemItr = request.getAccount().getItems().iterator();

                /*
                * Do to limitations on Phase I implementation, we can't get the
                * item list for reconciliation. So, we need to get some data
                * from the first item in the list and pop it into another API
                * field.
                */
                int i = 0;

                // Get list of invoices
                do
                {
                    lineItem = itemItr.next();

                    // PHASE I BEGIN
                    if (i < 1)
                    {
                        transaction.getRequest().setDescription (lineItem.getDescription());
                    }
                    // else do nothing

                    // PHASE I END

                    invoice = convertLineItemToInvoice (lineItem);
                    invoices.add (invoice);
                } while (itemItr.hasNext());

                // Populate the account and add it to the account list
                account.setAccountNo (request.getAccount().getAccountNo());
                account.setStatementNo (request.getAccount().getStatementNo());
                account.setInvoices (invoices);
                accounts.add (account);

                transaction.getRequest().setAmount (Double.valueOf (request.getAccount().getAmount()));
                transaction.setRefId (request.getClientData().getGuid());
                transaction.getRequest().setAccountList (accounts);
                transaction.getRequest().setTransInvoiceNo (request.getAccount().getStatementNo());
//// IMPLEMENT FOR PHASE II
////                    transaction.getRequest().setDescription ("Payment for account " + account.getAccountNo()
////                                                             + ", Date: " + today.getTime()
////                                                             + ", Statement: " + request.getAccount().getStatementNo()
////                                                            );
//// PHASE II END

                transaction = methods.requestTransaction (transaction, params);
                lresponse.setGuid (transaction.getRefId());
                lresponse.setAuthCode (transaction.getResponse().getAuthCode());
                lresponse.setTransactionId (transaction.getResponse().getTransactionId());

                if (transaction.getRefId().equals (request.getClientData().getGuid())
                    && transaction.getResponse().getErrorCode() == TRANSACTION_SUCCESS)
                {
                    lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                    lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());

                    if (transaction.getRequest().getCustomer() != null)
                    {
////                        lresponse.setCustAccount (transaction.getRequest().getUser().getAccountNum());
////                        lresponse.setProfileToken (transaction.getRequest().getCustomer().getProfileToken());
                    }
                }
                else
                {
                    if (transaction.getResponse().getErrorCode() != TRANSACTION_SUCCESS)
                    {
                        lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
                        lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
                    }
                    else
                    {
                        lresponse.setErrorMsg (TRANS_GUID_MISMATCH);
                        lresponse.setErrorCode ("" + TRANSACTION_GUID_ERR);
                    }
                }
            }
            catch (NumberFormatException e)
            {
                lresponse.setErrorCode ("" + API_TRANSACTION_CANCELED);
                lresponse.setErrorMsg (TRANS_PAY_FAILED);
                lresponse.setTransactionId ("0");
                lresponse.setGuid (transaction.getRefId());

                log.setErrorMsg (TRANS_INVALID_PARAM);
                log.setErrorCode (lresponse.getErrorCode());
                log.setCustomerId (request.getClientData().getCustAccount());
                log.setAmount (request.getAccount().getAmount());
                log.setErrorType (TRANS_REQUEST);
                log.setLogText (TRANS_AMT_INVALID);

                Logger.log (0, PortalImpl.class, log, true);
            }
        }
        else
        {
            lresponse.setErrorMsg (transaction.getResponse().getErrorMessage());
            lresponse.setErrorCode ("" + transaction.getResponse().getErrorCode());
            lresponse.setTransactionId ("0");
            lresponse.setGuid (transaction.getRefId());
        }

        // Make sure client is invalid before return
        client = null;
        params.getRequest().removeAttribute ("clientBean");

        return lresponse;
    }
}
