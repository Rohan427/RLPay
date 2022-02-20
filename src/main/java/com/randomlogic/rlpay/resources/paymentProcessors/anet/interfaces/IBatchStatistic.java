/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

import java.math.BigDecimal;

/**
 *
 * @author Paul.Allen
 */
public interface IBatchStatistic
{

    /**
     * @return the accountType
     */
    String getAccountType();

    /**
     * @return the chargeAmount
     */
    BigDecimal getChargeAmount();

    /**
     * @return the chargeChargeBackAmount
     */
    BigDecimal getChargeChargeBackAmount();

    /**
     * @return the chargeChargeBackCount
     */
    Integer getChargeChargeBackCount();

    /**
     * @return the chargeCount
     */
    int getChargeCount();

    /**
     * @return the chargeReturnedItemsAmount
     */
    BigDecimal getChargeReturnedItemsAmount();

    /**
     * @return the chargeReturnedItemsCount
     */
    Integer getChargeReturnedItemsCount();

    /**
     * @return the chargebackAmount
     */
    BigDecimal getChargebackAmount();

    /**
     * @return the chargebackCount
     */
    Integer getChargebackCount();

    /**
     * @return the correctionNoticeCount
     */
    Integer getCorrectionNoticeCount();

    /**
     * @return the declineCount
     */
    int getDeclineCount();

    /**
     * @return the errorCount
     */
    int getErrorCount();

    /**
     * @return the refundAmount
     */
    BigDecimal getRefundAmount();

    /**
     * @return the refundChargeBackAmount
     */
    BigDecimal getRefundChargeBackAmount();

    /**
     * @return the refundChargeBackCount
     */
    Integer getRefundChargeBackCount();

    /**
     * @return the refundCount
     */
    int getRefundCount();

    /**
     * @return the refundReturnedItemsAmount
     */
    BigDecimal getRefundReturnedItemsAmount();

    /**
     * @return the refundReturnedItemsCount
     */
    Integer getRefundReturnedItemsCount();

    /**
     * @return the returnedItemAmount
     */
    BigDecimal getReturnedItemAmount();

    /**
     * @return the returnedItemCount
     */
    Integer getReturnedItemCount();

    /**
     * @return the voidCount
     */
    int getVoidCount();

    /**
     * @param accountType the accountType to set
     */
    void setAccountType (String accountType);

    /**
     * @param chargeAmount the chargeAmount to set
     */
    void setChargeAmount (BigDecimal chargeAmount);

    /**
     * @param chargeChargeBackAmount the chargeChargeBackAmount to set
     */
    void setChargeChargeBackAmount (BigDecimal chargeChargeBackAmount);

    /**
     * @param chargeChargeBackCount the chargeChargeBackCount to set
     */
    void setChargeChargeBackCount (Integer chargeChargeBackCount);

    /**
     * @param chargeCount the chargeCount to set
     */
    void setChargeCount (int chargeCount);

    /**
     * @param chargeReturnedItemsAmount the chargeReturnedItemsAmount to set
     */
    void setChargeReturnedItemsAmount (BigDecimal chargeReturnedItemsAmount);

    /**
     * @param chargeReturnedItemsCount the chargeReturnedItemsCount to set
     */
    void setChargeReturnedItemsCount (Integer chargeReturnedItemsCount);

    /**
     * @param chargebackAmount the chargebackAmount to set
     */
    void setChargebackAmount (BigDecimal chargebackAmount);

    /**
     * @param chargebackCount the chargebackCount to set
     */
    void setChargebackCount (Integer chargebackCount);

    /**
     * @param correctionNoticeCount the correctionNoticeCount to set
     */
    void setCorrectionNoticeCount (Integer correctionNoticeCount);

    /**
     * @param declineCount the declineCount to set
     */
    void setDeclineCount (int declineCount);

    /**
     * @param errorCount the errorCount to set
     */
    void setErrorCount (int errorCount);

    /**
     * @param refundAmount the refundAmount to set
     */
    void setRefundAmount (BigDecimal refundAmount);

    /**
     * @param refundChargeBackAmount the refundChargeBackAmount to set
     */
    void setRefundChargeBackAmount (BigDecimal refundChargeBackAmount);

    /**
     * @param refundChargeBackCount the refundChargeBackCount to set
     */
    void setRefundChargeBackCount (Integer refundChargeBackCount);

    /**
     * @param refundCount the refundCount to set
     */
    void setRefundCount (int refundCount);

    /**
     * @param refundReturnedItemsAmount the refundReturnedItemsAmount to set
     */
    void setRefundReturnedItemsAmount (BigDecimal refundReturnedItemsAmount);

    /**
     * @param refundReturnedItemsCount the refundReturnedItemsCount to set
     */
    void setRefundReturnedItemsCount (Integer refundReturnedItemsCount);

    /**
     * @param returnedItemAmount the returnedItemAmount to set
     */
    void setReturnedItemAmount (BigDecimal returnedItemAmount);

    /**
     * @param returnedItemCount the returnedItemCount to set
     */
    void setReturnedItemCount (Integer returnedItemCount);

    /**
     * @param voidCount the voidCount to set
     */
    void setVoidCount (int voidCount);

}
