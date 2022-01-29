/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.interfaces.IInvoice;

/**
 *
 * @author Paul.Allen
 */
public class Invoice implements IInvoice, Serializable
{
    private String customerPO = null;
    private String orderNumber = null;
    private String invoiceNumber = null;
    private String description = null;
    private Date invoiceDate = null;
    private Date dueDate = null;
    private String orginalAmount = "0";
    private String openAmount = "0";

    public Invoice()
    {

    }

    /**
     * @return the orderNumber
     */
    @Override
    public String getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    @Override
    public void setOrderNumber (String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the invoiceNumber
     */
    @Override
    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    @Override
    public void setInvoiceNumber (String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * @return the description
     */
    @Override
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    @Override
    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     * @return the invoiceDate
     */
    @Override
    public Date getInvoiceDate()
    {
        return invoiceDate;
    }

    /**
     * @param invoiceDate the invoiceDate to set
     */
    @Override
    public void setInvoiceDate (Date invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }

    /**
     * @return the dueDate
     */
    @Override
    public Date getDueDate()
    {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    @Override
    public void setDueDate (Date dueDate)
    {
        this.dueDate = dueDate;
    }

    /**
     * @return the orginalAmount
     */
    @Override
    public String getOrginalAmount()
    {
        return orginalAmount;
    }

    /**
     * @param orginalAmount the orginalAmount to set
     */
    @Override
    public void setOrginalAmount (String orginalAmount)
    {
        this.orginalAmount = orginalAmount;
    }

    /**
     * @return the openAmount
     */
    @Override
    public String getOpenAmount()
    {
        return openAmount;
    }

    /**
     * @param openAmount the openAmount to set
     */
    @Override
    public void setOpenAmount (String openAmount)
    {
        this.openAmount = openAmount;
    }

    /**
     * @return the customerPO
     */
    @Override
    public String getCustomerPO()
    {
        return customerPO;
    }

    /**
     * @param customerPO the customerPO to set
     */
    @Override
    public void setCustomerPO (String customerPO)
    {
        this.customerPO = customerPO;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof Invoice)
        {
            if (hashCode() == ((Invoice) other).hashCode())
            {
                result = true;
            }
            else
            {

            }
        }
        else
        {
            ;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 2;
        hash = 53 * hash + Objects.hashCode (this.invoiceNumber);
        hash = 53 * hash + Objects.hashCode (this.description);
        return hash;
    }

    @Override
    public String toString()
    {
        return "Invoice: " + this.invoiceNumber + ", " + this.description + ", " + this.invoiceDate;
    }
}
