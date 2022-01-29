/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.interfaces;

import com.randomlogic.rlpay.model.domain.company.entity.ConfigurationId;

/**
 *
 * @author Paul.Allen
 */
public interface IConfiguration extends IDomainObject
{
    ConfigurationId getId();

    String getValue();

    void setId (ConfigurationId id);

    void setValue (String value);

    int getEncLevel();

    void setEncLevel (int encLevel);

    String getEncType();

    void setEncType (String encType);
}
