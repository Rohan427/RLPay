/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.entity;
// Generated Jan 7, 2019 10:43:39 AM by Hibernate Tools 4.3.1


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ConfigurationId generated by hbm2java
 */
@Embeddable
public class ConfigurationId  implements Serializable
{
    private int idConfig = 0;
    private String configKey = "";

    public ConfigurationId()
    {
    }

    public ConfigurationId (int idconfig, String configKey)
    {
       this.idConfig = idconfig;
       this.configKey = configKey;
    }

    @Column (name = "idConfiguration", unique = true, nullable = false)
    public int getIdConfig()
    {
        return this.idConfig;
    }

    public void setIdConfig (int idConfig)
    {
        this.idConfig = idConfig;
    }

    @Column (name = "configKey", nullable = false, length = 45)
    public String getConfigKey()
    {
        return this.configKey;
    }

    public void setConfigKey (String configKey)
    {
        this.configKey = configKey;
    }

    @Override
    public boolean equals (Object other)
    {
        if (this == other)
        {
            return true;
        }

        if (other == null)
        {
            return false;
        }

        if (!(other instanceof ConfigurationId))
        {
            return false;
        }

        ConfigurationId castOther = (ConfigurationId) other;

        return (this.getIdConfig() == castOther.getIdConfig())
                && ((this.getConfigKey().equals (castOther.getConfigKey()))
                || (this.getConfigKey() != null && castOther.getConfigKey() != null && this.getConfigKey().equals (castOther.getConfigKey()))
               );
    }

    @Override
    public int hashCode()
    {
        int result = 17;

        result = 37 * result + this.getIdConfig();
        result = 37 * result + (getConfigKey() == null ? 0 : this.getConfigKey().hashCode());
        return result;
    }
}


