/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.entity;
// Generated Jan 7, 2019 10:43:39 AM by Hibernate Tools 4.3.1


import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.randomlogic.rlpay.model.domain.interfaces.IConfiguration;

/**
 * Configuration generated by hbm2java
 */
@Entity
@Table
(
    name = "Configuration",
    catalog = "rlapi",
    uniqueConstraints = @UniqueConstraint (columnNames = "idConfiguration")
)
public class Configuration implements IConfiguration, Serializable
{
    private static final long serialVersionUID = 7344055601161960295L;

    private ConfigurationId id;
    private String value;
    private int encLevel = 0;
    private String encType = "0";

    public Configuration()
    {
        id = new ConfigurationId();
    }

    public Configuration (String key)
    {
       this.id = new ConfigurationId();
       this.id.setConfigKey (key);
    }

    public Configuration (ConfigurationId id, String value)
    {
       this.id = id;
       this.value = value;
    }

    @EmbeddedId
    @AttributeOverrides
    (
        {
            @AttributeOverride (name = "idConfiguration", column=@Column (name = "idConfiguration", unique = true, nullable = false)),
            @AttributeOverride (name = "configKey", column = @Column (name = "configKey", nullable = false, length = 45))
        }
    )
    @Override
    public ConfigurationId getId()
    {
        return this.id;
    }

    @Override
    public void setId (ConfigurationId id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    @Column (name = "value", nullable = false)
    @Override
    public String getValue()
    {
        return this.value;
    }

    @Override
    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public int getEncLevel()
    {
        return this.encLevel;
    }

    @Override
    public void setEncLevel (int encLevel)
    {
        this.encLevel = encLevel;
    }

    @Override
    public String getEncType()
    {
        return this.encType;
    }

    @Override
    public void setEncType (String encType)
    {
        this.encType = encType;
    }
}
