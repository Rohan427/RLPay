/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IBatchStatistic;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class BatchStatistic implements IBatchStatistic
{
    private String accountType;
    private BigDecimal chargeAmount;
    private int chargeCount;
    private BigDecimal refundAmount;
    private int refundCount;
    private int voidCount;
    private int declineCount;
    private int errorCount;
    private BigDecimal returnedItemAmount;
    private Integer returnedItemCount;
    private BigDecimal chargebackAmount;
    private Integer chargebackCount;
    private Integer correctionNoticeCount;
    private BigDecimal chargeChargeBackAmount;
    private Integer chargeChargeBackCount;
    private BigDecimal refundChargeBackAmount;
    private Integer refundChargeBackCount;
    private BigDecimal chargeReturnedItemsAmount;
    private Integer chargeReturnedItemsCount;
    private BigDecimal refundReturnedItemsAmount;
    private Integer refundReturnedItemsCount;

    /**
     * @return the accountType
     */
    @Override
    public String getAccountType()
    {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    @Override
    public void setAccountType (String accountType)
    {
        this.accountType = accountType;
    }

    /**
     * @return the chargeAmount
     */
    @Override
    public BigDecimal getChargeAmount()
    {
        return chargeAmount;
    }

    /**
     * @param chargeAmount the chargeAmount to set
     */
    @Override
    public void setChargeAmount (BigDecimal chargeAmount)
    {
        this.chargeAmount = chargeAmount;
    }

    /**
     * @return the chargeCount
     */
    @Override
    public int getChargeCount()
    {
        return chargeCount;
    }

    /**
     * @param chargeCount the chargeCount to set
     */
    @Override
    public void setChargeCount (int chargeCount)
    {
        this.chargeCount = chargeCount;
    }

    /**
     * @return the refundAmount
     */
    @Override
    public BigDecimal getRefundAmount()
    {
        return refundAmount;
    }

    /**
     * @param refundAmount the refundAmount to set
     */
    @Override
    public void setRefundAmount (BigDecimal refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    /**
     * @return the refundCount
     */
    @Override
    public int getRefundCount()
    {
        return refundCount;
    }

    /**
     * @param refundCount the refundCount to set
     */
    @Override
    public void setRefundCount (int refundCount)
    {
        this.refundCount = refundCount;
    }

    /**
     * @return the voidCount
     */
    @Override
    public int getVoidCount()
    {
        return voidCount;
    }

    /**
     * @param voidCount the voidCount to set
     */
    @Override
    public void setVoidCount (int voidCount)
    {
        this.voidCount = voidCount;
    }

    /**
     * @return the declineCount
     */
    @Override
    public int getDeclineCount()
    {
        return declineCount;
    }

    /**
     * @param declineCount the declineCount to set
     */
    @Override
    public void setDeclineCount (int declineCount)
    {
        this.declineCount = declineCount;
    }

    /**
     * @return the errorCount
     */
    @Override
    public int getErrorCount()
    {
        return errorCount;
    }

    /**
     * @param errorCount the errorCount to set
     */
    @Override
    public void setErrorCount (int errorCount)
    {
        this.errorCount = errorCount;
    }

    /**
     * @return the returnedItemAmount
     */
    @Override
    public BigDecimal getReturnedItemAmount()
    {
        return returnedItemAmount;
    }

    /**
     * @param returnedItemAmount the returnedItemAmount to set
     */
    @Override
    public void setReturnedItemAmount (BigDecimal returnedItemAmount)
    {
        this.returnedItemAmount = returnedItemAmount;
    }

    /**
     * @return the returnedItemCount
     */
    @Override
    public Integer getReturnedItemCount()
    {
        return returnedItemCount;
    }

    /**
     * @param returnedItemCount the returnedItemCount to set
     */
    @Override
    public void setReturnedItemCount (Integer returnedItemCount)
    {
        this.returnedItemCount = returnedItemCount;
    }

    /**
     * @return the chargebackAmount
     */
    @Override
    public BigDecimal getChargebackAmount ()
    {
        return chargebackAmount;
    }

    /**
     * @param chargebackAmount the chargebackAmount to set
     */
    @Override
    public void setChargebackAmount (BigDecimal chargebackAmount)
    {
        this.chargebackAmount = chargebackAmount;
    }

    /**
     * @return the chargebackCount
     */
    @Override
    public Integer getChargebackCount()
    {
        return chargebackCount;
    }

    /**
     * @param chargebackCount the chargebackCount to set
     */
    @Override
    public void setChargebackCount (Integer chargebackCount)
    {
        this.chargebackCount = chargebackCount;
    }

    /**
     * @return the correctionNoticeCount
     */
    @Override
    public Integer getCorrectionNoticeCount()
    {
        return correctionNoticeCount;
    }

    /**
     * @param correctionNoticeCount the correctionNoticeCount to set
     */
    @Override
    public void setCorrectionNoticeCount (Integer correctionNoticeCount)
    {
        this.correctionNoticeCount = correctionNoticeCount;
    }

    /**
     * @return the chargeChargeBackAmount
     */
    @Override
    public BigDecimal getChargeChargeBackAmount()
    {
        return chargeChargeBackAmount;
    }

    /**
     * @param chargeChargeBackAmount the chargeChargeBackAmount to set
     */
    @Override
    public void setChargeChargeBackAmount (BigDecimal chargeChargeBackAmount)
    {
        this.chargeChargeBackAmount = chargeChargeBackAmount;
    }

    /**
     * @return the chargeChargeBackCount
     */
    @Override
    public Integer getChargeChargeBackCount()
    {
        return chargeChargeBackCount;
    }

    /**
     * @param chargeChargeBackCount the chargeChargeBackCount to set
     */
    @Override
    public void setChargeChargeBackCount (Integer chargeChargeBackCount)
    {
        this.chargeChargeBackCount = chargeChargeBackCount;
    }

    /**
     * @return the refundChargeBackAmount
     */
    @Override
    public BigDecimal getRefundChargeBackAmount()
    {
        return refundChargeBackAmount;
    }

    /**
     * @param refundChargeBackAmount the refundChargeBackAmount to set
     */
    @Override
    public void setRefundChargeBackAmount (BigDecimal refundChargeBackAmount)
    {
        this.refundChargeBackAmount = refundChargeBackAmount;
    }

    /**
     * @return the refundChargeBackCount
     */
    @Override
    public Integer getRefundChargeBackCount()
    {
        return refundChargeBackCount;
    }

    /**
     * @param refundChargeBackCount the refundChargeBackCount to set
     */
    @Override
    public void setRefundChargeBackCount (Integer refundChargeBackCount)
    {
        this.refundChargeBackCount = refundChargeBackCount;
    }

    /**
     * @return the chargeReturnedItemsAmount
     */
    @Override
    public BigDecimal getChargeReturnedItemsAmount()
    {
        return chargeReturnedItemsAmount;
    }

    /**
     * @param chargeReturnedItemsAmount the chargeReturnedItemsAmount to set
     */
    @Override
    public void setChargeReturnedItemsAmount (BigDecimal chargeReturnedItemsAmount)
    {
        this.chargeReturnedItemsAmount = chargeReturnedItemsAmount;
    }

    /**
     * @return the chargeReturnedItemsCount
     */
    @Override
    public Integer getChargeReturnedItemsCount()
    {
        return chargeReturnedItemsCount;
    }

    /**
     * @param chargeReturnedItemsCount the chargeReturnedItemsCount to set
     */
    @Override
    public void setChargeReturnedItemsCount (Integer chargeReturnedItemsCount)
    {
        this.chargeReturnedItemsCount = chargeReturnedItemsCount;
    }

    /**
     * @return the refundReturnedItemsAmount
     */
    @Override
    public BigDecimal getRefundReturnedItemsAmount()
    {
        return refundReturnedItemsAmount;
    }

    /**
     * @param refundReturnedItemsAmount the refundReturnedItemsAmount to set
     */
    @Override
    public void setRefundReturnedItemsAmount (BigDecimal refundReturnedItemsAmount)
    {
        this.refundReturnedItemsAmount = refundReturnedItemsAmount;
    }

    /**
     * @return the refundReturnedItemsCount
     */
    @Override
    public Integer getRefundReturnedItemsCount()
    {
        return refundReturnedItemsCount;
    }

    /**
     * @param refundReturnedItemsCount the refundReturnedItemsCount to set
     */
    @Override
    public void setRefundReturnedItemsCount (Integer refundReturnedItemsCount)
    {
        this.refundReturnedItemsCount = refundReturnedItemsCount;
    }
}
