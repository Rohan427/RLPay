/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.util;

import com.randomlogic.rlpay.application.config.Config;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pallen
 */
public class ServletParams implements Serializable
{
    private ErrorBean error = new ErrorBean();
    private String system = null;
    private ServletContext context = null;
    private String uri = null;
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private Config cfg = null;
    private IClient client = null;
    private IUser user = null;

    public ServletParams (ErrorBean error,
                          String system,
                          ServletContext context,
                          String uri,
                          HttpServletRequest request,
                          HttpServletResponse response
                         )
    {
        this.context = context;
        this.error = error;
        this.request = request;
        this.response = response;
        this.system = system;
        this.uri = uri;;

        cfg = (Config)this.context.getAttribute ("cfgBean");
    }

    public void setClient (IClient client)
    {
        this.request.getSession().setAttribute ("clientBean", client);
    }

    public ServletParams (ErrorBean error,
                          ServletContext context,
                          HttpServletRequest request,
                          HttpServletResponse response
                         )
    {
        this.context = context;
        this.error = error;
        this.request = request;
        this.response = response;
        this.uri = request.getRequestURI();

        cfg = (Config)this.context.getAttribute ("cfgBean");
    }

    /**
     * @return the error
     */
    public ErrorBean getError()
    {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError (ErrorBean error)
    {
        this.error = error;
    }

    public IUser getUser()
    {
        return (IUser)request.getSession().getAttribute ("userBean");
    }

    public void setUser (IUser user)
    {
        this.request.getSession().setAttribute ("userBean", user);
    }

    /**
     * @return the client
     */
    public IClient getClient()
    {
        return (IClient)request.getSession().getAttribute ("clientBean");
    }

    /**
     * @return the error
     */
    public ErrorBean getErrorBean()
    {
        return getError ();
    }

    /**
     * @param error the error to set
     */
    public void setErrorBean (ErrorBean error)
    {
        this.setError (error);
    }

    /**
     * @return the system
     */
    public String getSystem()
    {
        return system;
    }

    /**
     * @param system the system to set
     */
    public void setSystem (String system)
    {
        this.system = system;
    }

    /**
     * @return the context
     */
    public ServletContext getContext()
    {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext (ServletContext context)
    {
        this.context = context;
    }

    /**
     * @return the uri
     */
    public String getUri()
    {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri (String uri)
    {
        this.uri = uri;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest()
    {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest (HttpServletRequest request)
    {
        this.request = request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse()
    {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse (HttpServletResponse response)
    {
        this.response = response;
    }

    /**
     * @return the cfg
     */
    public Config getCfg()
    {
        return cfg;
    }

    /**
     * @param cfg the cfg to set
     */
    public void setCfg (Config cfg)
    {
        cfg = (Config)this.context.getAttribute ("cfgBean");
    }
}
