/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.portal.payment.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.randomlogic.rlpay.model.domain.payment.TransactionSetting;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.interfaces.IAccount;
import com.randomlogic.rlpay.model.domain.interfaces.IClient;
import com.randomlogic.rlpay.model.domain.interfaces.ICustomer;
import com.randomlogic.rlpay.model.domain.interfaces.IUser;
import com.randomlogic.rlpay.model.service.impl.ConfigAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.ITransactionSetting;
import com.randomlogic.rlpay.model.service.interfaces.Ipersist;

/**
 *
 * @author Paul.Allen
 */
public class TransRequest implements Serializable
{
    private ICustomer customer;

    private IClient client;

    private IUser user;

    private Double amount;// Total amount for all accounts

    private int transactionType;

    private String transInvoiceNo;

    private String description;

    private Collection<IAccount> accountList = new ArrayList<>();//List of accounts to pay or display

    private Map<String, String> paymentSettings = new HashMap<String, String>();//Map of hosted form settings

    private ApiNonce nonce = new ApiNonce();

    private Collection<ITransactionSetting> transactionSettings = new ArrayList<ITransactionSetting>();

    private ReportRequest reportReq;

    TransRequest()
    {
        Configuration config = new Configuration();
        Ipersist configDao = new ConfigAccessSvcImpl();
        TransactionSetting setting = new TransactionSetting();

        config.getId().setConfigKey ("duplicateWindow");
        config = (Configuration)configDao.find (config);
        configDao.close();

        // If no value loaded, set default
        if (config == null)
        {
            config = new Configuration();
            config.getId().setConfigKey ("duplicateWindow");
            config.setValue ("28800");
        }
        //else do nothing

        setting.setSettingName (config.getId().getConfigKey());
        setting.setSettingValue (config.getValue());

        transactionSettings.add (setting);
    }

    public ReportRequest getReportReq()
    {
        return this.reportReq;
    }

    public void setReportReq (ReportRequest reportReq)
    {
        this.reportReq = reportReq;
    }

    public Collection<ITransactionSetting> getTransactionSettings()
    {
        return this.transactionSettings;
    }

    public void setTransactionSettings (Collection<ITransactionSetting> transactionSettings)
    {
        this.transactionSettings = transactionSettings;
    }

    public ApiNonce getNonce()
    {
        return this.nonce;
    }

    public void setNonce (ApiNonce nonce)
    {
        this.nonce = nonce;
    }

    public IUser getUser()
    {
        return user;
    }

    public void setUser (IUser user)
    {
        this.user = user;
    }

    /**
     * @return the customer
     */
    public ICustomer getCustomer()
    {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer (ICustomer customer)
    {
        this.customer = customer;
    }

    /**
     * @return the amount
     */
    public Double getAmount()
    {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount (Double amount)
    {
        this.amount = amount;
    }

    /**
     * @return the transactionType
     */
    public int getTransactionType()
    {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType (int transactionType)
    {
        this.transactionType = transactionType;
    }

    /**
     * @return the transInvoiceNo
     */
    public String getTransInvoiceNo()
    {
        return transInvoiceNo;
    }

    /**
     * @param transInvoiceNo the transInvoiceNo to set
     */
    public void setTransInvoiceNo (String transInvoiceNo)
    {
        this.transInvoiceNo = transInvoiceNo;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription (String description)
    {
        this.description = description;
    }

    /**
     * @return the accountList
     */
    public Collection<IAccount> getAccountList()
    {
        return accountList;
    }

    /**
     * @param accountList the accountList to set
     */
    public void setAccountList (Collection<IAccount> accountList)
    {
        this.accountList = accountList;
    }

    /**
     * @return the paymentSettings
     */
    public Map<String, String> getPaymentSettings()
    {
        return paymentSettings;
    }

    /**
     * @param paymentSettings the paymentSettings to set
     */
    public void setPaymentSettings (Map<String, String> paymentSettings)
    {
        this.paymentSettings = paymentSettings;
    }

    /**
     * @return the client
     */
    public IClient getClient()
    {
        return client;
    }

    /**
     * @param client
     */
    public void setClient (IClient client)
    {
        this.client = client;
    }
}
