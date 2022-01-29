/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.interfaces;

import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;

/**
 *
 * @author Paul.Allen
 */
public interface IAuthenticate
{
    IAuthResponse authenticate();

    void init (IAuthParams params);
}
