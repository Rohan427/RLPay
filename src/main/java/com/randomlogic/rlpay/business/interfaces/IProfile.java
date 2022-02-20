/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.business.interfaces;

import java.util.Map;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.portal.payment.domain.TransactionRecord;

/**
 *
 * @author Paul.Allen
 */
public interface IProfile extends IPortalManager
{
    /**
     *
     * @param customer the value of user
     * @param environment the value of environment
     * @param refId the value of refId
     * @return the model.domain.interfaces.ICustomer
     */
    ICustomer getCustomer (ICustomer customer, String environment, String refId);

    IClient login (String password, String UID, String account);

    IClient verify (IClient client, ServletParams params);

    ICustomer register (ICustomer customer, String refId);

    ICustomer createPortalProfile (IUser user, Map<String, String> parameters, String environment, String refId);

    ICustomer deletePortalProfile (ICustomer customer);

    void setEnvironment (String environment);

    ICustomer getProfilePage (TransactionRecord transaction);
}
