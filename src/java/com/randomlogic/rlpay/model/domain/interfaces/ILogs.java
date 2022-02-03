/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.util.Date;

/**
 *
 * @author Paul.Allen
 */
public interface ILogs extends IDomainObject
{
    int getUsers();

    void setUsers (int users);

    String getClientip();

    void setClientip (String clientip);

    String getAuthCode();

    void setAuthCode (String authCode);

    String getAmount();

    void setAmount (String amount);

    String getGuid();

    void setGuid (String guid);

    String getMethod();

    void setMethod (String method);

    String getCommand();

    void setCommand (String command);

    String getBatchId();

    void setBatchId (String batchId);

    /**
     * @return the customerId
     */
    String getCustomerId();

    /**
     * @return the errorCode
     */
    String getErrorCode();

    /**
     * @return the errorMsg
     */
    String getErrorMsg();

    /**
     * @return the errorSource
     */
    String getErrorSource();

    /**
     * @return the errorType
     */
    String getErrorType();

    Integer getIdLogs();

    Date getLogDate();

    /**
     * @return the logText
     */
    String getLogText();

    /**
     * @return the transactionId
     */
    String getTransactionId();

    /**
     * @param customerId the customerId to set
     */
    void setCustomerId (String customerId);

    /**
     * @param errorCode the errorCode to set
     */
    void setErrorCode (String errorCode);

    /**
     * @param errorMsg the errorMsg to set
     */
    void setErrorMsg (String errorMsg);

    /**
     * @param errorSource the errorSource to set
     */
    void setErrorSource (String errorSource);

    /**
     * @param errorType the errorType to set
     */
    void setErrorType (String errorType);

    void setIdLogs (Integer idLogs);

    void setLogDate (Date logDate);

    /**
     * @param logText the logText to set
     */
    void setLogText (String logText);

    /**
     * @param transactionId the transactionId to set
     */
    void setTransactionId (String transactionId);
}
