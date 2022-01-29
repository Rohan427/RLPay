/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.sql.Date;

/**
 *
 * @author Paul.Allen
 */
public interface IInvoice
{
    /**
     * @return the customerPO
     */
    String getCustomerPO();

    /**
     * @return the description
     */
    String getDescription();

    /**
     * @return the dueDate
     */
    Date getDueDate();

    /**
     * @return the invoiceDate
     */
    Date getInvoiceDate();

    /**
     * @return the invoiceNumber
     */
    String getInvoiceNumber();

    /**
     * @return the openAmount
     */
    String getOpenAmount();

    /**
     * @return the orderNumber
     */
    String getOrderNumber();

    /**
     * @return the orginalAmount
     */
    String getOrginalAmount();

    /**
     * @param customerPO the customerPO to set
     */
    void setCustomerPO (String customerPO);

    /**
     * @param description the description to set
     */
    void setDescription (String description);

    /**
     * @param dueDate the dueDate to set
     */
    void setDueDate (Date dueDate);

    /**
     * @param invoiceDate the invoiceDate to set
     */
    void setInvoiceDate (Date invoiceDate);

    /**
     * @param invoiceNumber the invoiceNumber to set
     */
    void setInvoiceNumber (String invoiceNumber);

    /**
     * @param openAmount the openAmount to set
     */
    void setOpenAmount (String openAmount);

    /**
     * @param orderNumber the orderNumber to set
     */
    void setOrderNumber (String orderNumber);

    /**
     * @param orginalAmount the orginalAmount to set
     */
    void setOrginalAmount (String orginalAmount);
}
