/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.payment;

import com.randomlogic.rlpay.model.service.interfaces.ITransactionSetting;

/**
 *
 * @author Paul.Allen
 */
public class TransactionSetting implements ITransactionSetting
{
    private String settingName = null;

    private String settingValue = null;

    @Override
    public String getSettingName()
    {
        return this.settingName;
    }

    @Override
    public void setSettingName (String setting)
    {
        this.settingName = setting;
    }

    @Override
    public String getSettingValue()
    {
        return this.settingValue;
    }

    @Override
    public void setSettingValue (String value)
    {
        this.settingValue = value;
    }

}
