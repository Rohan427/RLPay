/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.monitor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class LogData implements Serializable
{
    private Integer idLog = null;
    private int users = 2;
    private String clientip = "0.0.0.0";
    private String guid = "0";
    private String method = "";
    private String command = "";
    private String transactionId = "0";
    private String batchId = "0";
    private String authCode = "none";
    private String amount = "0.00";
    private String customerId = "0";
    private String errorCode = "";
    private String errorType = "";
    private String errorMsg = "";
    private String logText = "";
    private String errorSource = "";
    private Date logDate;

    /**
     * @return the users
     */
    public int getUsers()
    {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers (int users)
    {
        this.users = users;
    }

    /**
     * @return the clientip
     */
    public String getClientip()
    {
        return clientip;
    }

    /**
     * @param clientip the clientip to set
     */
    public void setClientip (String clientip)
    {
        this.clientip = clientip;
    }

    /**
     * @return the guid
     */
    public String getGuid()
    {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid (String guid)
    {
        this.guid = guid;
    }

    /**
     * @return the method
     */
    public String getMethod()
    {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod (String method)
    {
        this.method = method;
    }

    /**
     * @return the command
     */
    public String getCommand()
    {
        return command;
    }

    /**
     * @param command the command to set
     */
    public void setCommand (String command)
    {
        this.command = command;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId()
    {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId (String transactionId)
    {
        this.transactionId = transactionId;
    }

    /**
     * @return the batchId
     */
    public String getBatchId()
    {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId (String batchId)
    {
        this.batchId = batchId;
    }

    /**
     * @return the authCode
     */
    public String getAuthCode()
    {
        return authCode;
    }

    /**
     * @param authCode the authCode to set
     */
    public void setAuthCode (String authCode)
    {
        this.authCode = authCode;
    }

    /**
     * @return the amount
     */
    public String getAmount()
    {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorType
     */
    public String getErrorType()
    {
        return errorType;
    }

    /**
     * @param errorType the errorType to set
     */
    public void setErrorType (String errorType)
    {
        this.errorType = errorType;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg()
    {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg (String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    /**
     * @return the logText
     */
    public String getLogText()
    {
        return logText;
    }

    /**
     * @param logText the logText to set
     */
    public void setLogText (String logText)
    {
        this.logText = logText;
    }

    /**
     * @return the errorSource
     */
    public String getErrorSource()
    {
        return errorSource;
    }

    /**
     * @param errorSource the errorSource to set
     */
    public void setErrorSource (String errorSource)
    {
        this.errorSource = errorSource;
    }

    /**
     * @return the logDate
     */
    public Date getLogDate()
    {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate (Date logDate)
    {
        this.logDate = logDate;
    }

    /**
     * @return the idLog
     */
    public Integer getIdLog()
    {
        return idLog;
    }

    /**
     * @param idLog the idLog to set
     */
    public void setIdLog (Integer idLog)
    {
        this.idLog = idLog;
    }
}
