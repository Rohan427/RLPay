/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.impl;

import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.business.interfaces.IPortalManager;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.*;
import com.randomlogic.rlpay.portal.payment.domain.ReportRequest;
import com.randomlogic.rlpay.portal.payment.domain.ReportResponse;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.portal.payment.service.impl.PortalMethodImpl;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ITransactionDetail;
import com.randomlogic.rlpay.view.parms.WebTransactionDetails;
import java.io.Serializable;
import java.util.Calendar;
import java.util.NoSuchElementException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Paul.Allen
 */
@Controller
public class ReportController implements Serializable
{
    private ServletParams params;
    private String resultPage = null;
    private User user = null;

    private HttpSession session;
    private String transId = "0";

    private ConfigBean cfgBean = null;

    @Autowired
    public PortalMethodImpl portal;

    @Autowired
    TransactionRecord transaction;

    public void setPortal (PortalMethodImpl portal)
    {
        this.portal = portal;
    }

    public void setTransId (String transId)
    {
        this.transId = transId;
    }

    public String getTransId()
    {
        return this.transId;
    }

    public HttpSession getSession()
    {
        return this.session;
    }

    public void setSession()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        session = (HttpSession) facesContext.getExternalContext().getSession (false);
        Object request;
        Object response;

        if (session == null)
        {
            user = new User();
            session.setAttribute ("userBean", user);
            session.setAttribute ("expired", true);
            resultPage = "/admin/report.xhtml";
        }
        else
        {
            user = (User)session.getAttribute ("userBean");
        }

        ServletContext temp = (ServletContext)facesContext.getExternalContext().getContext();
        request = facesContext.getExternalContext().getRequest();
        response = facesContext.getExternalContext().getResponse();

        params = new ServletParams (new ErrorBean(), temp, (HttpServletRequest)request, (HttpServletResponse)response);
        cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
    }

    public WebTransactionDetails details()
    {
        WebTransactionDetails transaction = new WebTransactionDetails();
        ReportRequest request = new ReportRequest();
        ReportResponse response;
        ITransactionDetail detail;
        String now = Long.toHexString (Calendar.getInstance().getTime().getTime());

        setSession();

        resultPage = "/admin/report.xhtml";

        request.setTransactionId (transId);
        request.setGuid (now);
        request.setCommand (CMD_DETAILS);
        request.setClient ((Client)params.getClient());

        response = report (request);

        try
        {
            detail = response.getTransactionReport().getTransactions().iterator().next();
            transaction.setAuthAmount (detail.getAuthAmount());
            transaction.setCustomerId (detail.getCustomer().getCustomerId());
            transaction.setAuthCode (detail.getTransaction().getAuthCode());
            transaction.setFirstName (detail.getCustomer().getDefaultPaymentProfile().getBilling().getFirstName());
            transaction.setLastName (detail.getCustomer().getDefaultPaymentProfile().getBilling().getLastName());
            transaction.setSubmitTime (detail.getTransaction().getSubmitTimeUTC().toString());
            transaction.setResponseCode ("" + detail.getTransaction().getResponseCode());
            transaction.setResponseDesc (detail.getTransaction().getResponseReasonDescription());
            params.getRequest().getSession().setAttribute ("transaction", transaction);
        }
        catch (NoSuchElementException | NullPointerException e)
        {
////            detail = new TransactionDetail();
        }

        Logger.log (ReportController.class, "ReportManager Details\n\n" + params.getUri());

        return transaction;
    }

    private ReportResponse report (ReportRequest request)
    {
        ReportResponse report = new ReportResponse();
        Logs log = new Logs();

        portal.setEnvironment (IPortalManager.ENV_SANDBOX);

        if (cfgBean != null)
        {
            if (cfgBean.getMode().equals ("prod"))
            {
                portal.setEnvironment (IPortalManager.ENV_PRODUCTION);
            }
            //else do nothing
        }
        // else do nothing

        log.setErrorSource (this.getClass().toString());
        log.setMethod (METH_REPORT);
        log.setCommand (request.getCommand());

        if (request.getGuid() != null)
        {
            transaction.getRequest().setReportReq (request);

            transaction.setMode (MODE_RECORDS);
            transaction.setCommand (request.getCommand());

            transaction = portal.requestTransaction (transaction, params);
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

        report.setClient ((Client)params.getClient());

        return report;
    }
}
