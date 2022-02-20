/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.service.interfaces;

import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;

/**
 *
 * @author Paul.Allen
 */
public interface ISSO
{

    boolean authenticate();

    IAuthResponse getResponse();

    void init (ServletParams params);

    boolean isEnabled();
}
