/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.monitor;

import java.io.Serializable;
import static com.randomlogic.rlpay.resources.paymentProcessors.config.PortalErrorCodes.*;

/**
 *
 * @author Paul.Allen
 */
public class ErrorMsgBean implements Serializable
{
    private int errorCode = TRANSACTION_SUCCESS;

    private String errorMessage = TRANS_MSG_SUCCESS;

    /**
     * @return the errorCode
     */
    public int getErrorCode()
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

    /**
     * @return the errorMessage
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage (String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
