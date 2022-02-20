/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Paul.Allen
 */
public interface IAuthParams
{
    /**
     *
     * @return
     */
    String getEnabled();

    /**
     * @return the authority
     */
    String getAuthority();

    /**
     * @return the clientId
     */
    String getClientId();

    /**
     * @return the request
     */
    HttpServletRequest getRequest();

    /**
     * @return the response
     */
    HttpServletResponse getResponse();

    /**
     * @return the secretKey
     */
    String getSecretKey();

    /**
     * @return the tenant
     */
    String getTenant();

    /**
     * @return the token
     */
    String getToken ();

    /**
     * @return the tokenName
     */
    String getTokenName();

    /**
     * @return the tokenResponseUrl
     */
    String getTokenResponseUrl();

    /**
     *
     * @param enabled
     */
    void setEnabled (String enabled);

    /**
     * @param authority the authority to set
     */
    void setAuthority (String authority);

    /**
     * @param clientId the clientId to set
     */
    void setClientId (String clientId);

    /**
     * @param request the request to set
     */
    void setRequest (HttpServletRequest request);

    /**
     * @param response the response to set
     */
    void setResponse (HttpServletResponse response);

    /**
     * @param secretKey the secretKey to set
     */
    void setSecretKey (String secretKey);

    /**
     * @param tenant the tenant to set
     */
    void setTenant (String tenant);

    /**
     * @param token the token to set
     */
    void setToken (String token);

    /**
     * @param tokenName the tokenName to set
     */
    void setTokenName (String tokenName);

    /**
     * @param tokenResponseUrl the tokenResponseUrl to set
     */
    void setTokenResponseUrl (String tokenResponseUrl);

}
