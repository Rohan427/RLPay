/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.entity;

import static com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureAPICodes.AZMSG_SUCCESS;
import static com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureAPICodes.AZURE_SUCCESS;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IExternalError;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class AzureError implements IExternalError
{
    private String message = AZMSG_SUCCESS;

    private Object source = null;

    private Exception exception = null;

    private int errorCode = AZURE_SUCCESS;

    /**
     * @return the message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage (String message)
    {
        this.message = message;
    }

    /**
     * @return the source
     */
    public Object getSource()
    {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource (Object source)
    {
        this.source = source;
    }

    /**
     * @return the exception
     */
    public Exception getException()
    {
        return exception;
    }

    /**
     * @param exception the exception to set
     */
    public void setException (Exception exception)
    {
        this.exception = exception;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode ()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (int errorCode)
    {
        this.errorCode = errorCode;
    }
}
