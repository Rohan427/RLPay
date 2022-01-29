/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.facade;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.randomlogic.rlpay.application.config.SpringContext;
import com.randomlogic.rlpay.business.interfaces.IPortalManager;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.Ipersist;
import com.randomlogic.rlpay.portal.payment.domain.ClientRequest;
import com.randomlogic.rlpay.portal.payment.domain.ClientResponse;
import com.randomlogic.rlpay.portal.payment.domain.LogSearchRequest;
import com.randomlogic.rlpay.portal.payment.domain.LogSearchResponse;
import com.randomlogic.rlpay.portal.payment.domain.PaymentRequest;
import com.randomlogic.rlpay.portal.payment.domain.ReportRequest;
import com.randomlogic.rlpay.portal.payment.domain.ReportResponse;
import com.randomlogic.rlpay.portal.payment.service.impl.PortalImpl;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
////@SchemaValidation
@WebService (serviceName = "Sandbox")
public class SandboxImpl
{
    private static final boolean DEBUG = true;

    @Resource
    private WebServiceContext webContext;

    private MessageContext msgContext;

    public PortalImpl portalSvc;

    private static final int ENVIRONMENT = IPortalManager.ENV_SANDBOX;

    public SandboxImpl()
    {

    }

    private void initContext()
    {
        msgContext = webContext.getMessageContext();
        portalSvc = SpringContext.getBean (PortalImpl.class);
        portalSvc.initContext (msgContext);
    }

    public void setPortalSvc (PortalImpl portalSvc)
    {
        this.portalSvc = portalSvc;
    }

    public PortalImpl getPortalSvc()
    {
        return this.portalSvc;
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "register")
    public ClientResponse register (@WebParam (name = "request") ClientRequest request)
    {
        initContext();
        return portalSvc.register (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "login")
    public ClientResponse login (@WebParam (name = "request") ClientRequest request)
    {
        initContext();
        return portalSvc.login (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "manage")
    public ClientResponse manage (@WebParam (name = "request") ClientRequest request)
    {
        initContext();
        return portalSvc.manage (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "payment")
    public ClientResponse payment (@WebParam (name = "request") PaymentRequest request)
    {
        initContext();
        return portalSvc.payment (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "log")
    public LogSearchResponse log (@WebParam (name = "request") LogSearchRequest request)
    {
        initContext();
        return portalSvc.log (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "deleteProfile")
    public ClientResponse deleteProfile (@WebParam (name = "request") ClientRequest request)
    {
        initContext();
        return portalSvc.deleteProfile (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "report")
    public ReportResponse report (@WebParam (name = "request") ReportRequest request)
    {
        initContext();
        return portalSvc.report (request, ENVIRONMENT);
    }

    /**
     * Web service operation
     * @param request
     * @return
     */
    @OPTIONS
    @POST
    @WebMethod (operationName = "test")
    public String test (@WebParam (name = "request") ClientRequest request)
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

    /**
     * Web service operation
     */
    @WebMethod (operationName = "auth")
    public String auth()
    {

        return null;
    }
}
