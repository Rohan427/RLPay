/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

////import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.interfaces.IConfiguration;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.service.interfaces.IConfigAccessSvc;
import com.randomlogic.rlpay.model.service.interfaces.IConfigSvcDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Service ("cfgSvc")
public class ConfigAccessSvcImpl implements IConfigAccessSvc
{
    @Autowired (required = true)
    IConfigSvcDAO cfgDao;

    /**
     *
     * @param type
     * @return
     */
    @Override
    public synchronized boolean delete (String type)
    {
        boolean result = false;

        return result;
    }

    @Override
    public synchronized IDomainObject find (IDomainObject object)
    {
        Configuration config;

        try
        {
            config = (Configuration)object;
            config = cfgDao.findByConfigKey (config.getId().getConfigKey());
        }
        catch (Exception e)
        {
            config = null;
            Logger.log (Logger.DEBUG, ConfigAccessSvcImpl.class, e);
        }

        return config;
    }

    @Override
    public synchronized boolean persist (Collection<?> object)
    {
        boolean result = true;

        return result;
    }

    @Override
    public synchronized boolean save (Collection<?> object)
    {
        boolean result = true;
        Configuration config;
        Iterator iterator;
        Collection<Configuration> configList;

        if (object != null)//validate
        {
            configList = (Collection<Configuration>)object;

            try
            {
                iterator = configList.iterator();

                while (iterator.hasNext() && result)
                {
                    config = (Configuration)iterator.next();
                    cfgDao.save (config);
                }
            }
            catch (Exception e)
            {
                result = false;
                Logger.log (Logger.EXCEPTION, ConfigAccessSvcImpl.class, e, "Exception saving Configuration collection");
            }
        }
        else
        {
            Logger.log (ConfigAccessSvcImpl.class, "Config.save failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public Configuration readConfig (String configKey)
    {
        return (Configuration)find (new Configuration (configKey));
    }

    @Override
    public synchronized Collection<Configuration> readConfigs()
    {
        Collection<Configuration> configs = new ArrayList<Configuration>();

        try
        {
            cfgDao.findAll().forEach (configs::add);
        }
        catch (Exception e)
        {
            Logger.log (Logger.DEBUG, ConfigAccessSvcImpl.class, e);
        }

        return configs;
    }

    @Override
    public synchronized boolean removeConfig (IConfiguration config)
    {
        return false;
    }

    @Override
    public boolean updateConfig (IConfiguration config, boolean isUpdate)
    {
        boolean result = false;

        if (isUpdate)
        {
            if (cfgDao.existsById (config.getId().getIdConfig()))
            {
                config = cfgDao.save ((Configuration)config);
                result = true;
            }
        }

        return result;
    }

    @Override
    public synchronized boolean merge (Collection<?> object)
    {
        boolean result = true;

        return result;
    }

    @Override
    public synchronized void close()
    {

    }
}
