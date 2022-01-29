/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Paul.Allen
 */
public class LineItem implements Serializable
{
    private boolean taxable = false;

    private String name;

    private String itemNo;

    private String orderNo;

    private String CustomerPo;

    private Date invoiceDate;

    private Date dueDate;

    private String description;

    private String openAmount;

    private String originalAmount;

    private int qty;

    public boolean isTaxable()
    {
        return this.taxable;
    }

    public void setTaxable (boolean taxable)
    {
        this.taxable = taxable;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * @return the itemNo
     */
    public String getItemNo()
    {
        return itemNo;
    }

    /**
     * @param itemNo the itemNo to set
     */
    public void setItemNo (String itemNo)
    {
        this.itemNo = itemNo;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo()
    {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo (String orderNo)
    {
        this.orderNo = orderNo;
    }

    /**
     * @return the CustomerPo
     */
    public String getCustomerPo()
    {
        return CustomerPo;
    }

    /**
     * @param CustomerPo the CustomerPo to set
     */
    public void setCustomerPo (String CustomerPo)
    {
        this.CustomerPo = CustomerPo;
    }

    /**
     * @return the invoiceDate
     */
    public Date getInvoiceDate()
    {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    public void setInvoiceDate (Date invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate()
    {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate (Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     * @return the openAmount
     */
    public String getOpenAmount()
    {
        return openAmount;
    }

    /**
     * @param openAmount the openAmount to set
     */
    public void setOpenAmount (String openAmount)
    {
        this.openAmount = openAmount;
    }

    /**
     * @return the originalAmount
     */
    public String getOriginalAmount()
    {
        return originalAmount;
    }

    /**
     * @param originalAmount the originalAmount to set
     */
    public void setOriginalAmount (String originalAmount)
    {
        this.originalAmount = originalAmount;
    }

    /**
     * @return the qty
     */
    public int getQty ()
    {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty (int qty)
    {
        this.qty = qty;
    }
}
