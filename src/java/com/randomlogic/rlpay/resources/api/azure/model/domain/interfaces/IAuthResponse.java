/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.StateData;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface IAuthResponse
{
    boolean isValid();

    void setValid (boolean valid);

    String getRedirectUrl();

    void setRedirectUrl (String redirectUrl);

    Map<String, StateData> getStateData();

    void setStateData (Map<String, StateData> stateData);

    Object getErrorObject();

    void setErrrorObject (Object errorObject);

    HttpServletRequest getRequest();

    void setRequest (HttpServletRequest request);

    HttpServletResponse getResponse();

    void setResponse (HttpServletResponse response);
}
