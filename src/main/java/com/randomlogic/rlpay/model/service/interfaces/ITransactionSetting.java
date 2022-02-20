/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;

/**
 *
 * @author Paul.Allen
 */
public interface ITransactionSetting
{
    String getSettingName();

    void setSettingName (String setting);

    String getSettingValue();

    void setSettingValue (String calue);
}
