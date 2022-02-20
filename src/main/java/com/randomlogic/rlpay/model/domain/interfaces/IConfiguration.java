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

    String getCfgvalue();

    void setId (ConfigurationId id);

    void setCfgvalue (String value);

    int getEnclevel();

    void setEnclevel (int encLevel);

    String getEnctype();

    void setEnctype (String encType);
}
