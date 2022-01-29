/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.entity;

import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class AuthParams implements IAuthParams
{
    private String authority = "";
    private String tenant = "";
    private String clientId = "";
    private String secretKey = "";
    private String token = "";
    private String tokenName = "";
    private HttpServletRequest request = null;
    private HttpServletResponse response = null;
    private String tokenResponseUrl = "";
    private String enabled = "";

    @Override
    public String getEnabled()
    {
        return this.enabled;
    }

    /**
     * @return the authority
     */
    @Override
    public String getAuthority()
    {
        return authority;
    }

    /**
     * @param authority the authority to set
     */
    @Override
    public void setAuthority (String authority)
    {
        this.authority = authority;
    }

    /**
     * @return the tenant
     */
    @Override
    public String getTenant()
    {
        return tenant;
    }

    /**
     * @param tenant the tenant to set
     */
    @Override
    public void setTenant (String tenant)
    {
        this.tenant = tenant;
    }

    /**
     * @return the clientId
     */
    @Override
    public String getClientId()
    {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    @Override
    public void setClientId (String clientId)
    {
        this.clientId = clientId;
    }

    /**
     * @return the secretKey
     */
    @Override
    public String getSecretKey()
    {
        return secretKey;
    }

    /**
     * @param secretKey the secretKey to set
     */
    @Override
    public void setSecretKey (String secretKey)
    {
        this.secretKey = secretKey;
    }

    /**
     * @return the token
     */
    @Override
    public String getToken()
    {
        return token;
    }

    /**
     * @param token the token to set
     */
    @Override
    public void setToken (String token)
    {
        this.token = token;
    }

    /**
     * @return the tokenName
     */
    @Override
    public String getTokenName()
    {
        return tokenName;
    }

    /**
     * @param tokenName the tokenName to set
     */
    @Override
    public void setTokenName (String tokenName)
    {
        this.tokenName = tokenName;
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

    /**
     * @return the tokenResponseUrl
     */
    @Override
    public String getTokenResponseUrl ()
    {
        return tokenResponseUrl;
    }

    /**
     * @param tokenResponseUrl the tokenResponseUrl to set
     */
    @Override
    public void setTokenResponseUrl (String tokenResponseUrl)
    {
        this.tokenResponseUrl = tokenResponseUrl;
    }

    @Override
    public void setEnabled (String enabled)
    {
        this.enabled = enabled;
    }
}
