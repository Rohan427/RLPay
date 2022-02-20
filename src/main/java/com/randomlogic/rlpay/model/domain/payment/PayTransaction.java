/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.util.Date;

/**
 *
 * @author Paul.Allen
 */
public class PayTransaction
{
    private String transId = "0";
    private String refTransId = "";
    private String splitTenderId = "";
    private Date submitTimeUTC = null;
    private Date submitTimeLocal = null;
    private String transType = "none";
    private String transStatus = "none";
    private int responseCode = 0;
    private int responseReasonCode = 0;
    private String responseReasonDescription = "none";
    private String authCode = "none";
    private String AVSResponse = "none";
    private String cardCodeResponse = "none";
    private String CAVVResponse = "none";

    /**
     * @return the transId
     */
    public String getTransId()
    {
        return transId;
    }

    /**
     * @param transId the transId to set
     */
    public void setTransId (String transId)
    {
        this.transId = transId;
    }

    /**
     * @return the refTransId
     */
    public String getRefTransId()
    {
        return refTransId;
    }

    /**
     * @param refTransId the refTransId to set
     */
    public void setRefTransId (String refTransId)
    {
        this.refTransId = refTransId;
    }

    /**
     * @return the splitTenderId
     */
    public String getSplitTenderId()
    {
        return splitTenderId;
    }

    /**
     * @return the responseReasonDescription
     */
    public String getResponseReasonDescription()
    {
        return responseReasonDescription;
    }

    /**
     * @param responseReasonDescription the responseReasonDescription to set
     */
    public void setResponseReasonDescription (String responseReasonDescription)
    {
        this.responseReasonDescription = responseReasonDescription;
    }

    /**
     * @param splitTenderId the splitTenderId to set
     */
    public void setSplitTenderId (String splitTenderId)
    {
        this.splitTenderId = splitTenderId;
    }

    /**
     * @return the submitTimeUTC
     */
    public Date getSubmitTimeUTC()
    {
        return submitTimeUTC;
    }

    /**
     * @param submitTimeUTC the submitTimeUTC to set
     */
    public void setSubmitTimeUTC (Date submitTimeUTC)
    {
        this.submitTimeUTC = submitTimeUTC;
    }

    /**
     * @return the submitTimeLocal
     */
    public Date getSubmitTimeLocal()
    {
        return submitTimeLocal;
    }

    /**
     * @param submitTimeLocal the submitTimeLocal to set
     */
    public void setSubmitTimeLocal (Date submitTimeLocal)
    {
        this.submitTimeLocal = submitTimeLocal;
    }

    /**
     * @return the transType
     */
    public String getTransType()
    {
        return transType;
    }

    /**
     * @param transType the transType to set
     */
    public void setTransType (String transType)
    {
        this.transType = transType;
    }

    /**
     * @return the transStatus
     */
    public String getTransStatus()
    {
        return transStatus;
    }

    /**
     * @param transStatus the transStatus to set
     */
    public void setTransStatus (String transStatus)
    {
        this.transStatus = transStatus;
    }

    /**
     * @return the responseCode
     */
    public int getResponseCode()
    {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode (int responseCode)
    {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseReasonCode
     */
    public int getResponseReasonCode()
    {
        return responseReasonCode;
    }

    /**
     * @param responseReasonCode the responseReasonCode to set
     */
    public void setResponseReasonCode (int responseReasonCode)
    {
        this.responseReasonCode = responseReasonCode;
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
     * @return the AVSResponse
     */
    public String getAVSResponse()
    {
        return AVSResponse;
    }

    /**
     * @param AVSResponse the AVSResponse to set
     */
    public void setAVSResponse (String AVSResponse)
    {
        this.AVSResponse = AVSResponse;
    }

    /**
     * @return the cardCodeResponse
     */
    public String getCardCodeResponse()
    {
        return cardCodeResponse;
    }

    /**
     * @param cardCodeResponse the cardCodeResponse to set
     */
    public void setCardCodeResponse (String cardCodeResponse)
    {
        this.cardCodeResponse = cardCodeResponse;
    }

    /**
     * @return the CAVVResponse
     */
    public String getCAVVResponse()
    {
        return CAVVResponse;
    }

    /**
     * @param CAVVResponse the CAVVResponse to set
     */
    public void setCAVVResponse (String CAVVResponse)
    {
        this.CAVVResponse = CAVVResponse;
    }
}
