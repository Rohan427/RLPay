/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.impl;

import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.application.util.Util;
import com.randomlogic.rlpay.model.domain.interfaces.IAccount;
import com.randomlogic.rlpay.model.domain.interfaces.IInvoice;
import com.randomlogic.rlpay.model.domain.payment.Account;
import com.randomlogic.rlpay.model.domain.payment.Invoice;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;
import com.randomlogic.rlpay.portal.payment.domain.TransRequest;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.portal.payment.service.impl.PortalMethodImpl;
import java.util.ArrayList;
import java.util.Collection;
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
public class PaymentController
{
    private ServletParams params;
    private String resultPage = "/login/payResult.xhtml";
    private User user = null;

    private HttpSession session;

    private ConfigBean cfgBean = null;

    private String password = null;
    private String email = null;

    @Autowired
    private PortalMethodImpl portal;

    @Autowired
    TransactionRecord transaction;

    public void setPortal (PortalMethodImpl portal)
    {
        this.portal = portal;
    }

    public PortalMethodImpl getPortal()
    {
        return this.portal;
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
            resultPage = "/login/logResult.xhtml";
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

    private void payment()
    {
        IAccount account = new Account();
        IInvoice invoice;
        Collection<IInvoice> invoices = new ArrayList<>();
        Collection<IAccount> accounts = new ArrayList<>();
        String url;

        setSession();

        resultPage = "/payConfirm.jsp";
        transaction.setMode ("profile");
        transaction.setCommand ("load");
        transaction.getRequest().setUser (user);

        transaction.setRefId (Util.getGUID());
        transaction = portal.requestTransaction (transaction, params);

        if (transaction.getResponse().getErrorCode() == 0)
        {
            // setup some fake data for testing

            // invoice line items x2
            invoice = new Invoice();
            invoice.setCustomerPO ("2987-285-10");
            invoice.setOrderNumber ("7890236");
            invoice.setInvoiceNumber ("4590872");
            invoice.setDescription ("Invoice");
            invoice.setOpenAmount ("7590.20");

            invoices.add (invoice);

            invoice = new Invoice();
            invoice.setInvoiceNumber ("4590873");
            invoice.setDescription ("Finance Fee");
            invoice.setOpenAmount ("50.00");

            invoices.add (invoice);

            // account data
            account.setInvoices (invoices);
            account.setAccountNo (user.getAccountNum());
            accounts.add (account);

            //statement data
            transaction.getRequest().setTransInvoiceNo ("2525566");
            transaction.getRequest().setDescription ("Payment for account " + user.getAccountNum() + ", 11/25/2018, Statement 2525566");

            // transaction type
            transaction.setMode ("payment");
            transaction.setCommand ("payment");
            transaction.getRequest().setAccountList (accounts);
            transaction.getRequest().setAmount (7640.20);
            transaction.getRequest().setTransactionType (IPaymentSvc.TTYPE_AUTH_CAPTURE);

            // Add hosted page settings
            TransRequest request = transaction.getRequest();

            // TODO: Get this data from the configuration settings
            request.getPaymentSettings().put ("hostedPaymentButtonOptions", "{\"text\": \"Pay\"}");
            request.getPaymentSettings().put ("hostedPaymentOrderOptions", "{\"show\": true, \"merchantName\": \"Random Logic\"}");

            // Send the payment transaction request
            transaction = portal.requestTransaction (transaction, params);

            if (transaction.getResponse().getErrorCode() == 0)
            {
                user.setHostedUrl (params.getCfg().getTestPayment());
                params.getRequest().getSession().setAttribute ("token", transaction.getRequest().getCustomer().getToken());
            }
            else
            {
                //make sure there is no token
                user.setAuthToken ("0");
                params.getRequest().getSession().removeAttribute ("token");
            }
        }
        else
        {
            //make sure there is no token
            user.setAuthToken ("0");//TODO: Handle the failure?
            params.getRequest().getSession().removeAttribute ("token");
        }

        params.getRequest().getSession().setAttribute ("userBean", user);
    }
}
