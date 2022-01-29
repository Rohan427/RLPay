/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;

/**
 *
 * @author Paul.Allen
 */
public interface IAuthenticationSvc
{
    // TODO: This method will verify the login credentials with the Dynamics/Azure system
    IClient login (IClient client, String password);

    IUser register (IUser client, String password, String email);

    IClient verify (IClient client, ServletParams params);
}
