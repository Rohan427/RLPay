/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.bean;

import com.randomlogic.rlpay.view.controller.impl.PaymentController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
@ManagedBean
@SessionScoped
public class PaymentBean implements Serializable
{
    @Autowired
    private PaymentController controller;

    public void setControlller (PaymentController controller)
    {
        this.controller = controller;
    }
}
