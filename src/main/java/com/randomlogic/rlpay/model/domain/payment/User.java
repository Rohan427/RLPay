/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;

/**
 *
 * @author Paul.Allen
 */
public class User implements IUser, Serializable
{
    /** Implementor Customer ID used as MerchantCustomerID for Auth.Net */
    private String accountNum = "0";

    /** List of alternate accounts associated with this user */
    private Collection<Account> accounts = new ArrayList<Account>();

    /** Auth.Net Customer ID for referencing Auth.Net Customer profile */
    private String profileToken = "0";

    /** The user's login ID */
    private String userName = null;

    /** The user's email address */
    private String email = null;

    /** The link to the hosted page being requested */
    private String hostedUrl = null;

    private String password = null;

    /**
     * @return the accounts
     */
    @Override
    public Collection<Account> getAccounts()
    {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    @Override
    public void setAccounts (Collection<Account> accounts)
    {
        this.accounts = accounts;
    }

    /**
     * @return the userName
     */
    @Override
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    @Override
    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the accountNum
     */
    @Override
    public String getAccountNum()
    {
        return accountNum;
    }

    /**
     * @param accountNum the accountNum to set
     */
    @Override
    public void setAccountNum (String accountNum)
    {
        this.accountNum = accountNum;
    }

    /**
     * @return the profileToken
     */
    @Override
    public String getAuthToken()
    {
        return profileToken;
    }

    /**
     * @param profileToken the profileToken to set
     */
    @Override
    public void setAuthToken (String profileToken)
    {
        this.profileToken = profileToken;
    }

    /**
     * @return the hostedUrl
     */
    @Override
    public String getHostedUrl()
    {
        return hostedUrl;
    }

    /**
     * @param hostedUrl the hostedUrl to set
     */
    @Override
    public void setHostedUrl (String hostedUrl)
    {
        this.hostedUrl = hostedUrl;
    }

    /**
     * @return the email
     */
    @Override
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    @Override
    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public boolean equals (Object other)
    {
        boolean result = false;

        if (other instanceof User)
        {
            if (hashCode() == ((User) other).hashCode())
            {
                result = true;
            }
            else
            {

            }
        }
        else
        {
            ;
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode (this.accountNum);
        hash = 53 * hash + Objects.hashCode (this.userName);
        hash = 53 * hash + Objects.hashCode (this.email);
        return hash;
    }

    @Override
    public String toString()
    {
        return "User: " + this.accountNum + ", " + this.userName + ", " + this.email;
    }

    /**
     * @return the password
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    @Override
    public void setPassword (String password)
    {
        this.password = password;
    }
}
