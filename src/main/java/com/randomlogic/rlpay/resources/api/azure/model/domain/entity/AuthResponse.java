/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.entity;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IExternalError;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class AuthResponse implements IAuthResponse
{
    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    private boolean valid = false;

    private String redirectUrl = "";

    private Map<String, StateData> stateData = null;

    private IExternalError azureError = new AzureError();

    @Override
    public Object getErrorObject()
    {
        return this.azureError;
    }

    @Override
    public void setErrrorObject (Object errorObject)
    {
        this.azureError = (IExternalError)errorObject;
    }

    @Override
    public String getRedirectUrl()
    {
        return this.redirectUrl;
    }

    @Override
    public void setRedirectUrl (String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public Map<String, StateData> getStateData()
    {
        return this.stateData;
    }

    @Override
    public void setStateData (Map<String, StateData> stateData)
    {
        this.stateData = stateData;
    }

    @Override
    public boolean isValid()
    {
        return this.valid;
    }

    @Override
    public void setValid (boolean valid)
    {
        this.valid = valid;
    }

    /**
     * @return the request
     */
    @Override
    public HttpServletRequest getRequest()
    {
        return request;
    }

    /**
     * @param request the request to set
     */
    @Override
    public void setRequest (HttpServletRequest request)
    {
        this.request = request;
    }

    /**
     * @return the response
     */
    @Override
    public HttpServletResponse getResponse()
    {
        return response;
    }

    /**
     * @param response the response to set
     */
    @Override
    public void setResponse (HttpServletResponse response)
    {
        this.response = response;
    }
}
