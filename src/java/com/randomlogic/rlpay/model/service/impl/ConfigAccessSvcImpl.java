/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.randomlogic.rlpay.model.domain.company.entity.Configuration;
import com.randomlogic.rlpay.model.domain.interfaces.IConfiguration;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.service.dao.HibernateDAOSvc;
import com.randomlogic.rlpay.model.service.interfaces.IConfigAccessSvc;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class ConfigAccessSvcImpl extends HibernateDAOSvc implements IConfigAccessSvc
{
    public ConfigAccessSvcImpl()
    {
        super();
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public synchronized boolean delete (String type)
    {
        boolean result = false;
////        Transaction transaction;
////
////        initSession();
////
////        if (session != null)
////        {
////            transaction = session.beginTransaction();
////            session.createSQLQuery ("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
////            session.createSQLQuery ("TRUNCATE Configuration").executeUpdate();
////            session.createSQLQuery ("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
////
////            transaction.commit();
////            result = true;
////        }
        //else do nothing

        return result;
    }

    @Override
    public synchronized IDomainObject find (IDomainObject object)
    {
        Configuration config;
        NativeQuery result;
        List<Object[]> resultSet;

        initSession();

        if (session != null)
        {
            try
            {
                config = (Configuration)object;
                String query = "SELECT * FROM Configuration WHERE configKey = '"
                             + config.getId().getConfigKey()+ "'";
                result = session.createSQLQuery (query);
                resultSet = (List<Object[]>)result.list();

                if (!resultSet.isEmpty())
                {
                    for (Object[] element : resultSet)
                    {
                        try
                        {
                            config.getId().setIdConfig (Integer.parseInt (element[0].toString()));
                            config.setValue (element[2].toString());
                        }
                        catch (Exception e)
                        {
                            Logger.log (Logger.EXCEPTION, ConfigAccessSvcImpl.class, e);
                        }
                    }
                }
                else
                {
                    config = null;
                }
            }
            catch (Exception e)
            {
                config = null;
                Logger.log (Logger.DEBUG, ConfigAccessSvcImpl.class, e);
            }
        }
        else
        {
            config = null;
            Logger.log (ConfigAccessSvcImpl.class, "Config.find failed with a NULL session.");
        }

        return config;
    }

    @Override
    public synchronized boolean persist (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        Configuration newConfig;
        Iterator iterator;
        Collection<Configuration> configs;

        if (object != null)//validate
        {
            configs = (Collection<Configuration>) object;
            initSession();

            if (session!= null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = configs.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newConfig = (Configuration)iterator.next();
                        session.persist (newConfig);
                    }

                    transaction.commit();
                }
                catch (Exception e)
                {
                    result = false;

                    if (transaction != null)
                    {
                        try
                        {
                            Logger.log (Logger.EXCEPTION, ConfigAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (ConfigAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (ConfigAccessSvcImpl.class, "Config.persist failed with a NULL session.");
            }
        }
        else
        {
            Logger.log (ConfigAccessSvcImpl.class, "Config.persist failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public synchronized boolean save (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        Configuration newConfig;
        Iterator iterator;
        Collection<Configuration> users;

        if (object != null)//validate
        {
            users = (Collection<Configuration>)object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();

                    iterator = users.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newConfig = (Configuration)iterator.next();
                        session.save (newConfig);
                    }

                    transaction.commit();
                }
                catch (Exception e)
                {
                    result = false;

                    if (transaction != null)
                    {
                        try
                        {
                            Logger.log (Logger.EXCEPTION, ConfigAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (ConfigAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (ConfigAccessSvcImpl.class, "Config.save failed with a NULL session.");
                result = false;
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
        Query query;
        Iterator<Configuration> iterator;

        initSession();

        if (session != null)
        {
            try
            {
                query = session.createQuery ("from Configuration");
                iterator = (Iterator<Configuration>)query.iterate();

                while (iterator.hasNext())
                {
                    configs.add (iterator.next());
                }
            }
            catch (Exception e)
            {
                Logger.log (Logger.DEBUG, ConfigAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (ConfigAccessSvcImpl.class, "Config.readConfigs failed with a NULL session.");
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
        boolean result;
        Collection<Configuration> configs = new ArrayList<Configuration>();

        if (isUpdate)
        {
            // TODO: complete update code later
            result = true;
        }
        else
        {
            configs.add ((Configuration)config);
            result = merge (configs);
        }

        return result;
    }

    @Override
    public synchronized boolean merge (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        Configuration newConfig;
        Iterator iterator;
        Collection<Configuration> configs;

        if (object != null)//validate
        {
            configs = (Collection<Configuration>) object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = configs.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newConfig = (Configuration)iterator.next();
                        session.merge (newConfig);
                    }

                    transaction.commit();
                }
                catch (Exception e)
                {
                    result = false;

                    if (transaction != null)
                    {
                        try
                        {
                            Logger.log (Logger.EXCEPTION, ConfigAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (ConfigAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (ConfigAccessSvcImpl.class, "Config.merge failed with a NULL session.");
                result = false;
            }
        }
        else
        {
            Logger.log (ConfigAccessSvcImpl.class, "Config.merge failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public synchronized void close()
    {
        if (session !=null)
        {
            try
            {
                session.flush();
                session.close();
            }
            catch (Exception e)
            {

            }
            finally
            {
                session = null;
            }
        }
    }
}
