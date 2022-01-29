/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.util;

import java.util.UUID;

/**
 *
 * @author Paul.Allen
 */
public class Util
{
    public static String getGUID()
    {
        UUID refId = UUID.randomUUID();

        return refId.toString();
    }
}
