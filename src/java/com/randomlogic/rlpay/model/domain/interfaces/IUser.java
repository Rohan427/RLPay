/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import java.util.Collection;
import com.randomlogic.rlpay.model.domain.payment.Account;

/**
 *
 * @author Paul.Allen
 */
public interface IUser
{
    /**
     * @return the accountNum
     */
    String getAccountNum();

    /**
     * @return the accounts
     */
    Collection<Account> getAccounts();

    /**
     * @return the email
     */
    String getEmail();

    /**
     * @return the hostedUrl
     */
    String getHostedUrl();

    /**
     * @return the profileToken
     */
    String getAuthToken();

    /**
     * @return the userName
     */
    String getUserName();

    /**
     * @param accountNum the accountNum to set
     */
    void setAccountNum (String accountNum);

    /**
     * @param accounts the accounts to set
     */
    void setAccounts (Collection<Account> accounts);

    /**
     * @param email the email to set
     */
    void setEmail (String email);

    /**
     * @param hostedUrl the hostedUrl to set
     */
    void setHostedUrl (String hostedUrl);

    /**
     * @param profileToken the profileToken to set
     */
    void setAuthToken (String profileToken);

    /**
     * @param userName the userName to set
     */
    void setUserName (String userName);

    String getPassword();

    void setPassword (String password);
}
