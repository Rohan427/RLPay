/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import com.randomlogic.rlpay.model.domain.payment.CustomerProfile;

/**
 *
 * @author Paul.Allen
 */
public class ProfileRequest implements Serializable
{
    private ClientRequest clientData;
    private CustomerProfile customer;

}
