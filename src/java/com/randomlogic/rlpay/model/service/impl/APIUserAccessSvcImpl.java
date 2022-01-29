/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.model.domain.company.entity.User;
import com.randomlogic.rlpay.model.domain.interfaces.IAPIUser;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.service.dao.HibernateDAOSvc;
import com.randomlogic.rlpay.model.service.interfaces.IAPIUserAccessSvc;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
public class APIUserAccessSvcImpl extends HibernateDAOSvc implements IAPIUserAccessSvc
{
    public APIUserAccessSvcImpl()
    {
        super();
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public boolean delete (String type)
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
////            session.createSQLQuery ("TRUNCATE Users").executeUpdate();
////            session.createSQLQuery ("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
////
////            transaction.commit();
////            result = true;
////        }
        //else do nothing

        return result;
    }

    @Override
    public IDomainObject find (IDomainObject object)
    {
        User user = null;
        String query = null;
        NativeQuery result;
        List<Object[]> resultSet;
        initSession();
        Object tempToken;

        if (session != null)
        {
            user = (User)object;
            query = "SELECT * FROM Users WHERE \"uid\" = '"
                  + user.getId().getUid() + "'"
                  + " AND deviceid = '" + user.getId().getDeviceid() + "'";
            try
            {
                result = session.createSQLQuery (query);
                resultSet = (List<Object[]>)result.list();

                if (!resultSet.isEmpty())
                {
                    DateFormat format = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss.SSS", Locale.ENGLISH);
                    Date date;

                    user.getId().setIdUser (Integer.parseInt (resultSet.get (0)[0].toString()));
                    user.setPassword (resultSet.get (0)[3].toString());
                    user.setEmail (resultSet.get (0)[4].toString());
                    user.setValid (Byte.parseByte (resultSet.get (0)[5].toString()));

                    tempToken = resultSet.get (0)[6];

                    if (tempToken != null)
                    {
                        user.setToken (resultSet.get (0)[6].toString());
                    }
                    else
                    {
                        user.setToken (null);
                    }

                    user.setEncLevel (Integer.parseInt (resultSet.get (0)[7].toString()));
                    user.setEncType (resultSet.get (0)[8].toString());

                    date = format.parse (resultSet.get (0)[9].toString());
                    user.setCreation (date);
                }
                else
                {
                    user = null;
                }
            }
            catch (Exception e)
            {
                user = null;
                Logger.log (0, APIUserAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, ".APIUser.find failed with a NULL session.");
        }

        return user;
    }

    @Override
    public boolean persist (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        User newUser;
        Iterator iterator;
        Collection<User> users;

        if (object != null)//validate
        {
            users = (Collection<User>) object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = users.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newUser = (User)iterator.next();
                        session.persist (newUser);
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
                            Logger.log (0, APIUserAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (APIUserAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (APIUserAccessSvcImpl.class, "APIUser.persist failed with a NULL session.");
            }
        }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, "APIUser.persist failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public boolean save (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        User newUser;
        Iterator iterator;
        Collection<User> users;

        if (object != null)//validate
        {
            users = (Collection<User>)object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = users.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newUser = (User)iterator.next();
                        session.save (newUser);
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
                            Logger.log (0, APIUserAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (APIUserAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (APIUserAccessSvcImpl.class, "APIUser.save failed with a NULL session.");
                result = false;
            }
        }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, "APIUser.save failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public User readUser (Integer iduser)
    {
        return (User)find (new User (iduser));
    }

    @Override
    public Collection<User> readUsers()
    {
        Collection<User> users = new ArrayList<User>();
        Query query;
        Iterator<User> iterator;
        initSession();

        if (session != null)
        {
            try
            {
                query = session.createQuery ("from Users");
                iterator = (Iterator<User>)query.iterate();

                while (iterator.hasNext())
                {
                    users.add (iterator.next());
                }
            }
            catch (Exception e)
            {
                Logger.log (0, APIUserAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, "APIUser.readUsers failed with a NULL session.");
        }

        return users;
    }

    @Override
    public boolean removeUser (IAPIUser user)
    {
        return true;
    }

    @Override
    public boolean updateUser (IAPIUser user, boolean isUpdate)
    {
        boolean result;
        Collection<User> users = new ArrayList<User>();

        if (isUpdate)
        {
            // TODO: complete update code later
            result = true;
        }
        else
        {
            users.add ((User)user);
            result = merge (users);
        }

        return result;
    }

    @Override
    public boolean merge (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction;
        User newUser;
        Iterator iterator;
        Collection<User> users;

        if (object != null)//validate
        {
            users = (Collection<User>) object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = users.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newUser = (User)iterator.next();
                        session.merge (newUser);
                    }

                    transaction.commit();
                }
                catch (Exception e)
                {
                    Logger.log (0, APIUserAccessSvcImpl.class, e);
                }
            }
            else
            {
                Logger.log (APIUserAccessSvcImpl.class, "APIUser.merge failed with a NULL session.");
                result = false;
            }
        }
        else
        {
            Logger.log (APIUserAccessSvcImpl.class, "APIUser.merge failed with a NULL object.");
            result = false;
        }

        return result;
    }

    @Override
    public void close()
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
