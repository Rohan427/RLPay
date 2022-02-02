/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.impl;

import com.randomlogic.rlpay.business.interfaces.IPortalManager;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.randomlogic.rlpay.model.domain.payment.Client;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.service.facade.AnetSvcImpl;
import com.randomlogic.rlpay.model.service.impl.AuthenticationSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.IPaymentSvc;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.view.controller.bean.LoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.randomlogic.rlpay.portal.payment.service.impl.PortalMethodImpl;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;
import com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.application.util.Util;
import com.randomlogic.rlpay.model.service.interfaces.IAuthenticationSvc;

/**
 *
 * @author Paul.Allen
 */
@Controller
public class LoginController implements Serializable
{
    private ServletParams params;
    private String resultPage = "/login/logResult.xhtml";
    private User user = null;

    private HttpSession session;

    private ConfigBean cfgBean = null;

    private String password = null;
    private String email = null;

    @Autowired
    private PortalMethodImpl portal;

    private String loginId;
    private String accountNo;
    private String description;

    public void setPortal (PortalMethodImpl portal)
    {
        this.portal = portal;
    }

    public PortalMethodImpl getPortal()
    {
        return this.portal;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
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

    public void login()
    {
        String url;
        Client client;
        TransactionRecord transaction = new TransactionRecord();

        setSession();
        params.setUser (user);
        client = new Client();
        client.setClientUID (loginId);
        client.setDeviceId (LoginBean.getDeviceId());
        client.setPassword (password);
        params.setClient (client);

        transaction.getRequest().setCustomer (new CustomerProfile());
        transaction.getRequest().setUser (params.getUser());
        transaction.getRequest().setClient (params.getClient());
        transaction.setMode ("profile");
        transaction.setCommand ("login");

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

        transaction.setRefId (Util.getGUID());
        transaction = portal.requestTransaction (transaction, params);
        url = params.getCfg().getTestManage();

        user = (User)transaction.getRequest().getUser();
        params.setUser (user);

        if (transaction.getResponse().getErrorCode() != PortalErrorCodes.TRANSACTION_SUCCESS)
        {
            if (transaction.getResponse().getErrorCode() == PortalErrorCodes.API_SERVER_CLIENT_AUTH)
            {
                params.setClient (transaction.getRequest().getClient());
            }
        }
        else
        {
            params.getUser().setHostedUrl (url);
            params.getUser().setAuthToken (transaction.getRequest().getCustomer().getProfileToken());
            params.getRequest().getSession().setAttribute ("token", transaction.getRequest().getCustomer().getToken());
        }

        generatePage();
    }

    public void register()
    {
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        IAuthenticationSvc rlpaySvc = new AuthenticationSvcImpl();
        ICustomer customer;
        int environment;
        String url;

        setSession();

        user = new User();
        user.setUserName (loginId);
        user.setAccountNum (accountNo);
        user.setEmail (email);
        params.setUser (user);

        params.getRequest().getSession().setAttribute ("registered", "new");

        // Verify login with implementor system
        params.setUser ((IUser)rlpaySvc.register (params.getUser(), password, email));

        if (params.getUser().getAccountNum().equals ("0"))
        {
            ;
        }
        else
        {
            environment = IPaymentSvc.ENV_SANDBOX;
            url = params.getCfg().getTestManage();

            params.getUser().setHostedUrl (url);

            // Check to see if already registered
            paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
            paymentSvc.setTransactionKey (params.getCfg().getTestTransId());

            customer = paymentSvc.getCustomerProfile (null,
                                                      params.getUser().getAccountNum(),
                                                      null,
                                                      "testid",//TODO: Make this a real refId - ref generated by system to track requests
                                                      true,
                                                      true,
                                                      environment, false
                                                     );

            // Not registered, so create profile
            if (customer.getProfileToken().equals ("0"))
            {
                ;// Do nothing, user already set
            }
            // Already registered, so inform user
            else
            {
                params.getRequest().getSession().setAttribute ("registered", "registered");
            }

            resultPage = "/regResult.jsp";
        }

        generatePage();
    }

    public void deleteAccount()
    {
        TransactionRecord transaction = new TransactionRecord();
        IPaymentSvc paymentSvc = new AnetSvcImpl();
        IAuthenticationSvc rlpaySvc = new AuthenticationSvcImpl();
        ICustomer customer;
        String url;

        setSession();

        url = params.getCfg().getTestManage();

        user.setAccountNum (accountNo);
        user.setEmail (email);
        params.setUser (user);

        params.getRequest().getSession().setAttribute ("registered", "invalid");

        // Verify login with implementor system
        params.setUser ((IUser)rlpaySvc.register (params.getUser(), password, email));

        transaction.getRequest().setUser (params.getUser());
        transaction.getRequest().setClient (params.getClient());
        transaction.setMode ("profile");
        transaction.setCommand ("delete");

//        transaction = portal.requestTransaction (transaction, params);

////        if (params.getClient().getDeviceId().equals ("0"))
////        {
////            ;
////        }
////        else
////        {
////            portal.setEnvironment (IPortalManager.ENV_SANDBOX);
////
////            if (cfgBean != null)
////            {
////                if (cfgBean.getMode().equals ("prod"))
////                {
////                    portal.setEnvironment (IPortalManager.ENV_PRODUCTION);
////                }
////                //else do nothing
////            }
////            // else do nothing
////
////            params.getUser().setHostedUrl (url);
////
////            // Check to see if already registered
////            paymentSvc.setApiLoginId (params.getCfg().getTestApiLoginId());
////            paymentSvc.setTransactionKey (params.getCfg().getTestTransId());
////
////            customer = paymentSvc.getCustomerProfile (null,
////                                                      params.getUser().getAccountNum(),
////                                                      params.getUser().getEmail(),
////                                                      "testid",//TODO: Make this a real refId - ref generated by system to track requests
////                                                      true,
////                                                      true,
////                                                      environment, false
////                                                     );
////
////            // Not registered, so nothing to do but inform user
////            if (customer.getProfileToken().equals ("0"))
////            {
////                params.getUser().setAuthToken ("0");
////                params.getRequest().getSession().setAttribute ("registered", new String ("invalid"));
////                params.getRequest().getSession().removeAttribute ("userBean");
////            }
////            // Registered, so delete and update user
////            else
////            {
////                customer = paymentSvc.deleteCustomerProfile (customer, environment);
////
////                if (customer.getProfileToken().equals ("0"))
////                {
////                    params.getRequest().getSession().setAttribute ("registered", new String ("deleted"));
////                    params.getRequest().getSession().removeAttribute ("userBean");
////                    params.getRequest().setAttribute ("userBean", user);
////                }
////                else
////                {
////                    params.getRequest().getSession().setAttribute ("registered", new String ("failed"));
////                }
////            }
////
////            resultPage = "/regResult.jsp";
////        }

        generatePage();
    }

    public String getLoginId()
    {
        return this.loginId;
    }

    public void setLoginId (String loginId)
    {
        this.loginId = loginId;
    }

    public void setAccountNo (String accountNo)
    {
        this.accountNo = accountNo;
    }

    public String getAccountNo()
    {
        return this.accountNo;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

    private void generatePage()
    {
        try
        {
            params.getContext().getRequestDispatcher (resultPage).forward (params.getRequest(), params.getResponse());
        }
        catch (ServletException | IOException ex)
        {
            Logger.log (Logger.EXCEPTION, LoginController.class, ex, "LoginController: Exception.\n");

            if (params.getError() != null)
            {
                params.getRequest().getSession().removeAttribute ("errorMsg");
                params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
                try
                {
                    params.getError().generateError (params.getContext(),
                                                     params.getRequest(),
                                                     params.getResponse(),
                                                     "Configuration",
                                                     params.getUri(),
                                                     ErrorBean.ERROR_GENERAL_ERROR,
                                                     "Requested page not found",
                                                     "/genError.jsp",
                                                     ex.getMessage(),
                                                     ex, null
                                                    );
                }
                catch (ServletException | IOException ex1)
                {
                    Logger.log (Logger.EXCEPTION, LoginController.class, ex1);
                }
            }
        }
    }
}
