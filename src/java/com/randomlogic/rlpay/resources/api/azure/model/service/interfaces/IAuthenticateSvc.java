/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.resources.api.azure.model.service.interfaces;

import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface IAuthenticateSvc
{
    IAuthResponse authenticate ();

    void init (IAuthParams params);

}
