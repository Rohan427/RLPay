/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

/**
 *
 * @author Paul.Allen
 */
public class Order
{
    private String invoiceNumber = "0";
    private String description = "";
    private String purchaseOrderNo = "0";

    /**
     * @return the invoiceNumber
     */
    public String getInvoiceNumber()
    {
        return invoiceNumber;
    }

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    public void setInvoiceNumber (String invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
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
     * @return the purchaseOrderNo
     */
    public String getPurchaseOrderNo()
    {
        return purchaseOrderNo;
    }

    /**
     * @param purchaseOrderNo the purchaseOrderNo to set
     */
    public void setPurchaseOrderNo (String purchaseOrderNo)
    {
        this.purchaseOrderNo = purchaseOrderNo;
    }
}
