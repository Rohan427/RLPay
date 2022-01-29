/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.interfaces;


import java.util.Collection;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.interfaces.IConfiguration;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public interface IConfigAccessSvc extends Ipersist
{
    /**
     *
     * @param config
     * @param isUpdate
     * @return
     */
    public boolean updateConfig (IConfiguration config, boolean isUpdate);

    /**
     *
     * @param key
     * @param idConfig
     * @return
     */
    public Configuration readConfig (String key);

    /**
     *
     * @return
     */
    Collection<Configuration> readConfigs();

    /**
     *
     * @param config
     * @return
     */
    public boolean removeConfig (IConfiguration config);
}
