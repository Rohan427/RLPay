/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.domain.entity;

import java.util.Date;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class StateData
{
    private String nonce;
    private Date expirationDate;

    public StateData (String nonce, Date expirationDate)
    {
        this.nonce = nonce;
        this.expirationDate = expirationDate;
    }

    public String getNonce()
    {
        return nonce;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }
}
