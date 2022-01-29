/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.paymentProcessors.anet.domain;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;
import com.randomlogic.rlpay.model.domain.payment.PayTransaction;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IFraudFilter;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IReturnedItem;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.IShippingInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ISolutionInfo;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ISubscription;
import com.randomlogic.rlpay.resources.paymentProcessors.anet.interfaces.ITransactionDetail;
import net.authorize.api.contract.v1.TransactionDetailsType;
import com.randomlogic.rlpay.portal.payment.domain.LineItem;
import com.randomlogic.rlpay.portal.payment.domain.Order;

/**
 *
 * @author Paul.Allen
 */
@XmlRootElement
public class TransactionDetail implements ITransactionDetail
{
    private CustomerProfile customer = null;
    private PayTransaction transaction = new PayTransaction();
    private String fDSFilterAction = "none";

    @XmlAnyElement
    private Collection<IFraudFilter> fDSFilters = null;
    private Order order = new Order();
    private Batch batch = null;
    private Tax tax = null;
    private Duty duty = null;
    private Shipping shipping = null;
    private Collection<LineItem> lineItems = new ArrayList<LineItem>();
    private String prepaidBalanceRemaining = "0.00";
    private String requestedAmount = "0.00";
    private String authAmount = "0.00";
    private String settleAmount = "0.00";
    private IShippingInfo shippingInfo;
    private boolean recurringBilling = false;

    @XmlAnyElement
    private Collection<IReturnedItem> returnedItemList = new ArrayList<IReturnedItem>();

    private ISolutionInfo solution = new SolutionInfo();
    private String customerIP;
    private ISubscription subscription = new SubscriptionInfo();
    private String marketType;
    private String product;
    private String mobileDeviceId;
    private String refId;

    @Override
    public String getRefId()
    {
        return this.refId;
    }

    @Override
    public void setRefId (String refId)
    {
        this.refId = refId;
    }

    @Override
    public ISubscription getSubscription()
    {
        return this.subscription;
    }

    @Override
    public void setSubscription (ISubscription subscription)
    {
        this.subscription = subscription;
    }

    @Override
    public String getCustomerIP()
    {
        return this.customerIP;
    }

    @Override
    public void setCustomerIP (String customerIP)
    {
        this.customerIP = customerIP;
    }

    @Override
    public ISolutionInfo getSolution()
    {
        return solution;
    }

    @Override
    public void setSolution (ISolutionInfo solution)
    {
        this.solution = solution;
    }

    @Override
    public Collection<IReturnedItem> getReturnedItemList()
    {
        return this.returnedItemList;
    }

    @Override
    public void setReturnedItemList (Collection<IReturnedItem> returnedItemList)
    {
        this.returnedItemList = returnedItemList;
    }

    @Override
    public boolean isRecurringBilling()
    {
        return this.recurringBilling;
    }

    @Override
    public void setRecurringBilling (boolean recurringBilling)
    {
        this.recurringBilling = recurringBilling;
    }

    @Override
    public IShippingInfo getShippingInfo()
    {
        return this.shippingInfo;
    }

    @Override
    public void setShippingInfo (IShippingInfo shippingInfo)
    {
        this.shippingInfo = shippingInfo;
    }

    /**
     * @return the customer
     */
    @Override
    public CustomerProfile getCustomer()
    {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    @Override
    public void setCustomer (CustomerProfile customer)
    {
        this.customer = customer;
    }

    /**
     * @return the transaction
     */
    @Override
    public PayTransaction getTransaction()
    {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    @Override
    public void setTransaction (PayTransaction transaction)
    {
        this.transaction = transaction;
    }

    /**
     * @return the fDSFilterAction
     */
    @Override
    public String getfDSFilterAction()
    {
        return fDSFilterAction;
    }

    /**
     * @param fDSFilterAction the fDSFilterAction to set
     */
    @Override
    public void setfDSFilterAction (String fDSFilterAction)
    {
        this.fDSFilterAction = fDSFilterAction;
    }

    /**
     * @return the fDSFilters
     */
    @Override
    public Collection<IFraudFilter> getfDSFilters()
    {
        return fDSFilters;
    }

    /**
     * @param fDSFilters the fDSFilters to set
     */
    @Override
    public void setfDSFilters (Collection<IFraudFilter> fDSFilters)
    {
        this.fDSFilters = fDSFilters;
    }

    /**
     * @return the orders
     */
    @Override
    public Order getOrders()
    {
        return getOrder ();
    }

    /**
     * @param orders the orders to set
     */
    @Override
    public void setOrder (Order order)
    {
        this.order = order;
    }

    /**
     * @return the batch
     */
    @Override
    public Batch getBatch()
    {
        return batch;
    }

    /**
     * @param batch the batch to set
     */
    @Override
    public void setBatch (Batch batch)
    {
        this.batch = batch;
    }

    /**
     * @return the tax
     */
    @Override
    public Tax getTax()
    {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    @Override
    public void setTax (Tax tax)
    {
        this.tax = tax;
    }

    /**
     * @return the duty
     */
    @Override
    public Duty getDuty()
    {
        return duty;
    }

    /**
     * @param duty the duty to set
     */
    @Override
    public void setDuty (Duty duty)
    {
        this.duty = duty;
    }

    /**
     * @return the shipping
     */
    @Override
    public Shipping getShipping()
    {
        return shipping;
    }

    /**
     * @param shipping the shipping to set
     */
    @Override
    public void setShipping (Shipping shipping)
    {
        this.shipping = shipping;
    }

    /**
     * @return the lineItems
     */
    @Override
    public Collection<LineItem> getLineItems()
    {
        return lineItems;
    }

    /**
     * @param lineItems the lineItems to set
     */
    @Override
    public void setLineItems (Collection<LineItem> lineItems)
    {
        this.lineItems = lineItems;
    }

    /**
     * @return the prepaidBalanceRemaining
     */
    @Override
    public String getPrepaidBalanceRemaining()
    {
        return prepaidBalanceRemaining;
    }

    /**
     * @param prepaidBalanceRemaining the prepaidBalanceRemaining to set
     */
    @Override
    public void setPrepaidBalanceRemaining (String prepaidBalanceRemaining)
    {
        this.prepaidBalanceRemaining = prepaidBalanceRemaining;
    }

    /**
     * @return the requestedAmount
     */
    @Override
    public String getRequestedAmount()
    {
        return requestedAmount;
    }

    /**
     * @param requestedAmount the requestedAmount to set
     */
    @Override
    public void setRequestedAmount (String requestedAmount)
    {
        this.requestedAmount = requestedAmount;
    }

    /**
     * @return the authAmount
     */
    @Override
    public String getAuthAmount()
    {
        return authAmount;
    }

    /**
     * @param authAmount the authAmount to set
     */
    @Override
    public void setAuthAmount (String authAmount)
    {
        this.authAmount = authAmount;
    }

    /**
     * @return the settleAmount
     */
    @Override
    public String getSettleAmount()
    {
        return settleAmount;
    }

    /**
     * @param settleAmount the settleAmount to set
     */
    @Override
    public void setSettleAmount (String settleAmount)
    {
        this.settleAmount = settleAmount;
    }

    @Override
    public void setShipping (TransactionDetailsType details)
    {
        throw new UnsupportedOperationException ("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the order
     */
    @Override
    public Order getOrder ()
    {
        return order;
    }

    /**
     * @return the marketType
     */
    @Override
    public String getMarketType ()
    {
        return marketType;
    }

    /**
     * @param marketType the marketType to set
     */
    @Override
    public void setMarketType (String marketType)
    {
        this.marketType = marketType;
    }

    /**
     * @return the product
     */
    @Override
    public String getProduct ()
    {
        return product;
    }

    /**
     * @param product the product to set
     */
    @Override
    public void setProduct (String product)
    {
        this.product = product;
    }

    /**
     * @return the mobileDeviceId
     */
    @Override
    public String getMobileDeviceId ()
    {
        return mobileDeviceId;
    }

    /**
     * @param mobileDeviceId the mobileDeviceId to set
     */
    @Override
    public void setMobileDeviceId (String mobileDeviceId)
    {
        this.mobileDeviceId = mobileDeviceId;
    }
}
