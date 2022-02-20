/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.bean;

import com.randomlogic.rlpay.view.controller.impl.ReportController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.randomlogic.rlpay.view.parms.WebTransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
@ManagedBean
@SessionScoped
public class ReportBean implements Serializable
{
    private WebTransactionDetails transaction;

    @Autowired
    private ReportController controller;

    public void setControlller (ReportController controller)
    {
        this.controller = controller;
    }

    public void details()
    {
        transaction = controller.details();
    }

    public void setTransId (String transId)
    {
        controller.setTransId (transId);
    }

    public String getTransId()
    {
        return controller.getTransId();
    }

    public void setTransaction (WebTransactionDetails transaction)
    {
        this.transaction = transaction;
    }

    public WebTransactionDetails getTransaction()
    {
        return this.transaction;
    }
}
