/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.service.impl;

import java.util.Collection;
import com.randomlogic.rlpay.application.monitor.ErrorMsgBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.business.interfaces.IPayment;
import com.randomlogic.rlpay.business.interfaces.IPortalManager;
import com.randomlogic.rlpay.business.interfaces.IProfile;
import com.randomlogic.rlpay.business.interfaces.IRecords;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IPayTransactionReport;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.service.impl.LogsAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;
import com.randomlogic.rlpay.portal.payment.domain.LogSearchRequest;
import com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul.Allen
 */
@Service
public class PortalMethodImpl
{
    private int environment = IPortalManager.ENV_SANDBOX;
    private TransactionRecord transaction;
    private ServletParams params;
    private String refId;

    @Autowired
    private IProfile profileManager;

    @Autowired
    private IPayment paymentManager;

    @Autowired
    private IRecords reportManager;

    public PortalMethodImpl()
    {

    }

    public void setProfileManager (IProfile profileManager)
    {
        this.profileManager = profileManager;
    }

    public void setPaymentManager (IPayment paymentManager)
    {
        this.paymentManager = paymentManager;
    }

    public void setReportManager (IRecords reportManager)
    {
        this.reportManager = reportManager;
    }

    public void setEnvironment (int environment)
    {
        this.environment = environment;
    }

    public int getEnvironment()
    {
        return this.environment;
    }

    public TransactionRecord requestTransaction (TransactionRecord transaction, ServletParams params)
    {
        this.transaction = transaction;
        this.params = params;

        refId = transaction.getRefId();

        switch (transaction.getMode())
        {
            case "profile":
                processProfileCommand();
                break;

            case "payment":
                processPaymentCommand();
                break;

            case "records":
                processRecordsCommand();
                break;

            default:
                this.transaction.getResponse().setErrorMessage ("Invalid command");
                break;
        }

        return this.transaction;
    }

    public Collection<Logs> requestLogs (LogSearchRequest request, ServletParams params)
    {
        ILogsAccessSvc logSvc = new LogsAccessSvcImpl();
        Collection<Logs> logList;

        logList = logSvc.searchLogs (request.getLog(), request.getBeginDate(), request.getEndDate());

        return logList;
    }

    private void processProfileCommand()
    {
        switch (transaction.getCommand())
        {
            case "login":
                profileLogin();
                break;

            case "register":
                profileRegister();
                break;

            case "manage":
                profileManage();
                break;

            case "load":
                profileLoad();
                break;

            case "delete":
                profileDelete();
                break;

            case "verify":
                verify();
                break;

            default:
                this.transaction.getResponse().setErrorMessage ("Invalid command");
                break;
        }
    }

    private void processPaymentCommand()
    {
        switch (transaction.getCommand())
        {
            case "payment":
                makeProfilePayment();
                break;

            case "noncepayment":
                noncePayment();
                break;

            default:
                this.transaction.getResponse().setErrorMessage ("Invalid command");
                break;
        }
    }

    private void processRecordsCommand()
    {
        switch (transaction.getCommand())
        {
            case "batchlist":
                batchList();
                break;

            case "translist":
                transList();
                break;

            case "unsettled":
                unsettledList();
                break;

            case "profilelist":
                profileList();
                break;

            case "details":
                transDetails();
                break;

            case "statistics":
                batchStats();
                break;

            case "updatersum":
                updaterSum();
                break;

            case "updaterdetails":
                updaterDetails();
                break;

            case "settled":
                settledList();
                break;

            default:
                this.transaction.getResponse().setErrorMessage ("Invalid command");
                break;
        }
    }

    private void makeProfilePayment()
    {
        paymentManager.initManager (params, environment);
        transaction.getRequest().setCustomer (paymentManager.getPaymentPage (transaction));
    }

    private void noncePayment()
    {
        paymentManager.initManager (params, environment);
        transaction = paymentManager.processpayment (transaction);
    }

    private void verify()
    {
        profileManager.initManager (params, environment);
        transaction.getRequest().setClient ((Client)profileManager.verify (transaction.getRequest().getClient(),
                                                                           params
                                                                          ));

        if (transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.API_SERVER_CLIENT_AUTH);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
        // Here when client is authenticated
        else
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_SUCCESS);
            transaction.getResponse().setErrorMessage (TRANS_MSG_SUCCESS);

            // Set the session info for authenticated client
            transaction.getRequest().getClient().setPassword (null);
            params.setClient ((Client)transaction.getRequest().getClient());
        }
    }

    private void profileLogin()
    {
        profileManager.initManager (params, environment);
        transaction.getRequest().setClient ((Client)profileManager.login (transaction.getRequest().getClient().getPassword(),
                                                                          transaction.getRequest().getClient().getClientUID(),
                                                                          transaction.getRequest().getClient().getDeviceId()
                                                                         )
                                           );

        if (transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_FAILED);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
        }
        // Here when client is authenticated
        else
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_SUCCESS);
            transaction.getResponse().setErrorMessage (TRANS_MSG_SUCCESS);

            // Set the session info for authenticated client
            transaction.getRequest().getClient().setPassword (null);
            params.setClient ((Client)transaction.getRequest().getClient());

            /* Only get a profile if the following is true:
            *   An Account Number and email are provided --OR--
            *   A profile token is provided
            */
            if (transaction.getRequest().getCustomer().getCustomerId().isEmpty())
            {
                transaction.getRequest().getCustomer().setCustomerId ("0");
            }
            else if (transaction.getRequest().getCustomer().getProfileToken().isEmpty())
            {
                transaction.getRequest().getCustomer().setProfileToken ("0");
            }
            //else do nothing

            if (!transaction.getRequest().getCustomer().getCustomerId().equals ("0"))
            {
                transaction.getRequest().setCustomer (profileManager.getCustomer (transaction.getRequest().getCustomer(), null, refId));

                if (transaction.getRequest().getCustomer().getProfileToken().equals ("0"))
                {
                    transaction.getResponse().setErrorCode (PortalErrorCodes.ANET_PROFILE_LOAD);
                    transaction.getResponse().setErrorMessage (TRANS_AUTH_NO_PROFILE);
                }
                else
                {
                    transaction.getRequest().setCustomer (profileManager.getProfilePage (transaction));

                    // Error code processing:
                    if (transaction.getRequest().getCustomer().getToken() == null)
                    {
                        ErrorMsgBean error = (ErrorMsgBean)transaction.getRequest().getCustomer().getErrorResponse();
                        Logger.log (this.getClass(), error.getErrorCode(), error.getErrorMessage());
                        transaction.getResponse().setErrorCode (error.getErrorCode());
                        transaction.getResponse().setErrorMessage (error.getErrorMessage());
                    }
                    else
                    {
                        transaction.getResponse().setErrorCode (TRANSACTION_SUCCESS);
                        transaction.getResponse().setErrorMessage (TRANS_MSG_SUCCESS);
                        Logger.log (this.getClass(), transaction.getResponse().getErrorCode(), transaction.getResponse().getErrorMessage());
                    }
                }
            }
            else
            {
                transaction.getResponse().setErrorCode (API_SERVER_CLIENT_AUTH);
                transaction.getResponse().setErrorMessage (TRANS_AUTH_NO_PROFILE);
            }
        }
    }

    private void profileRegister()
    {
        ErrorMsgBean errorBean;
        ICustomer customer = null;

        profileManager.initManager (params, environment);
        transaction.getRequest().setClient ((Client)profileManager.verify (transaction.getRequest().getClient(), params));

        if (!transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            // must include all three customer criteria: account, email, and description
            if (verifyCustomerData())
            {
                // Must not already be registered. We have to use all customer data to search
                // due to the way the Auth.Net payment processor handles profiles.
                customer = profileManager.getCustomer (transaction.getRequest().getCustomer(), null, refId);

                if (customer.getProfileToken().equals ("0"))
                {
                    transaction.getRequest().setCustomer (profileManager.register (transaction.getRequest().getCustomer(), transaction.getRefId()));
                    errorBean = (ErrorMsgBean)transaction.getRequest().getCustomer().getErrorResponse();
                    transaction.getResponse().setErrorCode (errorBean.getErrorCode());
                    transaction.getResponse().setErrorMessage (errorBean.getErrorMessage());
                }
                else
                {
                    transaction.getResponse().setErrorCode (PortalErrorCodes.ANET_PROFILE_CREATE);
                    transaction.getResponse().setErrorMessage (TRANS_PROF_EXISTS);
                }
            }
            // else do nothing
        }
        else
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_NOAUTH);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
    }

    private void profileManage()
    {
        profileManager.initManager (params, environment);

        transaction.getRequest().setClient ((Client)profileManager.verify (transaction.getRequest().getClient(), params));

        if (transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_FAILED);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
        else
        {
            // must include all three customer criteria: account, email, and description
            if (verifyCustomerData())
            {
                transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_SUCCESS);
                transaction.getResponse().setErrorMessage (TRANS_MSG_SUCCESS);

                // Must include an account
                if (!transaction.getRequest().getCustomer().getCustomerId().equals ("0"))
                {
                    transaction.getRequest().setCustomer (profileManager.getCustomer (transaction.getRequest().getCustomer(), null, refId));

                    if (transaction.getRequest().getCustomer().getProfileToken().equals ("0"))
                    {
                        transaction.getResponse().setErrorCode (PortalErrorCodes.ANET_PROFILE_LOAD);
                        transaction.getResponse().setErrorMessage (TRANS_AUTH_NO_PROFILE);
                    }
                    else
                    {
                        transaction.getRequest().setCustomer (profileManager.getProfilePage (transaction));

                        // Error code processing:
                        if (transaction.getRequest().getCustomer().getToken() == null)
                        {
                            ErrorMsgBean error = (ErrorMsgBean)transaction.getRequest().getCustomer().getErrorResponse();
                            Logger.log (this.getClass(), error.getErrorCode(), error.getErrorMessage());
                            transaction.getResponse().setErrorCode (error.getErrorCode());
                            transaction.getResponse().setErrorMessage (error.getErrorMessage());
                        }
                        else
                        {
                            transaction.getResponse().setErrorCode (TRANSACTION_SUCCESS);
                            transaction.getResponse().setErrorMessage (TRANS_MSG_SUCCESS);
                            Logger.log (this.getClass(), transaction.getResponse().getErrorCode(), transaction.getResponse().getErrorMessage());
                        }
                    }
                }
                else
                {
                    transaction.getResponse().setErrorCode (ANET_ACCOUNT_EXPECTED);
                    transaction.getResponse().setErrorMessage (TRANS_ACCOUNT_RQD);
                    Logger.log (this.getClass(), transaction.getResponse().getErrorCode(), transaction.getResponse().getErrorMessage());
                }
            }
            //else do nothing
        }
    }

    private void profileLoad()
    {
        profileManager.initManager (params, environment);

        transaction.getRequest().setClient ((Client)profileManager.verify (transaction.getRequest().getClient(), params));

        if (!transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            transaction.getRequest().setCustomer (profileManager.getCustomer (transaction.getRequest().getCustomer(), null, refId));

            if (transaction.getRequest().getCustomer().getProfileToken().equals ("0"))
            {
                transaction.getResponse().setErrorCode (PortalErrorCodes.ANET_PROFILE_LOAD);
                transaction.getResponse().setErrorMessage ("Failed to load payment profile");
            }
            else
            {
                transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_SUCCESS);
                transaction.getResponse().setErrorMessage ("Success");
            }
        }
        else
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_NOAUTH);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
    }

    private void profileDelete()
    {
        ErrorMsgBean errorBean;

        profileManager.initManager (params, environment);

        if (!transaction.getRequest().getClient().getAuthToken().equals ("0"))
        {
            transaction.getRequest().setCustomer (profileManager.deletePortalProfile (transaction.getRequest().getCustomer()));
            errorBean = (ErrorMsgBean)transaction.getRequest().getCustomer().getErrorResponse();
            transaction.getResponse().setErrorCode (errorBean.getErrorCode());
            transaction.getResponse().setErrorMessage (errorBean.getErrorMessage());
        }
        else
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_NOAUTH);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
    }

    private boolean verifyCustomerData()
    {
        boolean result = true;
        ICustomer customer = transaction.getRequest().getCustomer();

        if (customer.getCustomerId() == null
         || customer.getCustomerId().equals ("")
         || customer.getCustomerId().equals ("0")
           )
        {
            result = false;
        }
        // else do nothing

        if (customer.getEmail() == null
         || customer.getEmail().equals (""))
        {
            result = false;
        }
        // else do nothing

        if (customer.getDescription() == null
         || customer.getDescription().equals (""))
        {
            result = false;
        }
        // else do nothing

        // On failure, set error condition
        if (!result)
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.ANET_PROFILE_CREATE);
            transaction.getResponse().setErrorMessage (TRANS_INVALID_PARAM);
        }
        // else do nothing

        return result;
    }

    private void transDetails()
    {
        IPayTransactionReport report;
////        IRecords reportManager = (IRecords)PortalFactory.getPortalManager (transaction, params, environment);
        reportManager.initManager (params, environment);

        transaction.getRequest().getReportReq().setClient ((Client)reportManager.verify (transaction.getRequest().getReportReq().getClient(), params));

        if (transaction.getRequest().getReportReq().getClient().getAuthToken().equals ("0"))
        {
            transaction.getResponse().setErrorCode (PortalErrorCodes.TRANSACTION_FAILED);
            transaction.getResponse().setErrorMessage (TRANS_NOT_AUTH);
            transaction.getResponse().setTransactionId ("0");
        }
        else
        {
            report = reportManager.getTransactionDetails (transaction);
            transaction.getResponse().setTransactionreport (report);
            transaction.setRefId (report.getRefId());
            transaction.getResponse().setDescription (report.getDescription());
            transaction.getResponse().setErrorCode (report.getErrorCode());
            transaction.getResponse().setErrorMessage (report.getErrorMessage());
            transaction.getResponse().setResponseCode (report.getResponseCode());
        }
    }

    private void unsettledList()
    {
////        IRecords reportManager = (IRecords)PortalFactory.getPortalManager (transaction, params, environment);
        reportManager.initManager (params, environment);
    }

    private void settledList()
    {
////        IRecords reportManager = (IRecords)PortalFactory.getPortalManager (transaction, params, environment);
        reportManager.initManager (params, environment);
    }

    private void batchList()
    {
        reportManager.initManager (params, environment);
    }

    private void transList()
    {
        reportManager.initManager (params, environment);
    }

    private void profileList()
    {
        reportManager.initManager (params, environment);
    }

    private void batchStats()
    {
        reportManager.initManager (params, environment);
    }

    private void updaterSum()
    {
        reportManager.initManager (params, environment);
    }

    private void updaterDetails()
    {
        reportManager.initManager (params, environment);
    }
}
