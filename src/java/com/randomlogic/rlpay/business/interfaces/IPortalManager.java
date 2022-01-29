/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.interfaces;

import com.randomlogic.rlpay.application.util.ServletParams;

/**
 *
 * @author Paul.Allen
 */
public interface IPortalManager
{
    public static final int ENV_SANDBOX     = 0;
    public static final int ENV_PRODUCTION  = 1;

    void initManager (ServletParams newParams, int newEnv);
}
