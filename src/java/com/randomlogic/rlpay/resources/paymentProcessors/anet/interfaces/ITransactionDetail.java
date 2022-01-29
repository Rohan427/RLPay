/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces;

import java.util.Collection;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.PayTransaction;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Batch;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Duty;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Shipping;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.domain.Tax;
import net.authorize.api.contract.v1.TransactionDetailsType;
import com.randomlogic.rlpay.portal.payment.domain.LineItem;
import com.randomlogic.rlpay.portal.payment.domain.Order;

/**
 *
 * @author Paul.Allen
 */
public interface ITransactionDetail
{
    /**
     * @return the authAmount
     */
    String getAuthAmount();

    /**
     * @return the batch
     */
    Batch getBatch();

    /**
     * @return the customer
     */
    CustomerProfile getCustomer();

    String getCustomerIP();

    /**
     * @return the duty
     */
    Duty getDuty();

    /**
     * @return the lineItems
     */
    Collection<LineItem> getLineItems();

    /**
     * @return the marketType
     */
    String getMarketType();

    /**
     * @return the mobileDeviceId
     */
    String getMobileDeviceId();

    /**
     * @return the order
     */
    Order getOrder();

    /**
     * @return the orders
     */
    Order getOrders();

    /**
     * @return the prepaidBalanceRemaining
     */
    String getPrepaidBalanceRemaining();

    /**
     * @return the product
     */
    String getProduct();

    String getRefId();

    /**
     * @return the requestedAmount
     */
    String getRequestedAmount();

    Collection<IReturnedItem> getReturnedItemList();

    /**
     * @return the settleAmount
     */
    String getSettleAmount();

    /**
     * @return the shipping
     */
    Shipping getShipping();

    IShippingInfo getShippingInfo();

    ISolutionInfo getSolution();

    ISubscription getSubscription();

    /**
     * @return the tax
     */
    Tax getTax();

    /**
     * @return the transaction
     */
    PayTransaction getTransaction();

    /**
     * @return the fDSFilterAction
     */
    String getfDSFilterAction();

    /**
     * @return the fDSFilters
     */
    Collection<IFraudFilter> getfDSFilters();

    boolean isRecurringBilling();

    /**
     * @param authAmount the authAmount to set
     */
    void setAuthAmount (String authAmount);

    /**
     * @param batch the batch to set
     */
    void setBatch (Batch batch);

    /**
     * @param customer the customer to set
     */
    void setCustomer (CustomerProfile customer);

    void setCustomerIP (String customerIP);

    /**
     * @param duty the duty to set
     */
    void setDuty (Duty duty);

    /**
     * @param lineItems the lineItems to set
     */
    void setLineItems (Collection<LineItem> lineItems);

    /**
     * @param marketType the marketType to set
     */
    void setMarketType (String marketType);

    /**
     * @param mobileDeviceId the mobileDeviceId to set
     */
    void setMobileDeviceId (String mobileDeviceId);

    /**
     * @param orders the orders to set
     */
    void setOrder (Order order);

    /**
     * @param prepaidBalanceRemaining the prepaidBalanceRemaining to set
     */
    void setPrepaidBalanceRemaining (String prepaidBalanceRemaining);

    /**
     * @param product the product to set
     */
    void setProduct (String product);

    void setRecurringBilling (boolean recurringBilling);

    void setRefId (String refId);

    /**
     * @param requestedAmount the requestedAmount to set
     */
    void setRequestedAmount (String requestedAmount);

    void setReturnedItemList (Collection<IReturnedItem> returnedItemList);

    /**
     * @param settleAmount the settleAmount to set
     */
    void setSettleAmount (String settleAmount);

    /**
     * @param shipping the shipping to set
     */
    void setShipping (Shipping shipping);

    void setShipping (TransactionDetailsType details);

    void setShippingInfo (IShippingInfo shippingInfo);

    void setSolution (ISolutionInfo solution);

    void setSubscription (ISubscription subscription);

    /**
     * @param tax the tax to set
     */
    void setTax (Tax tax);

    /**
     * @param transaction the transaction to set
     */
    void setTransaction (PayTransaction transaction);

    /**
     * @param fDSFilterAction the fDSFilterAction to set
     */
    void setfDSFilterAction (String fDSFilterAction);

    /**
     * @param fDSFilters the fDSFilters to set
     */
    void setfDSFilters (Collection<IFraudFilter> fDSFilters);
}
