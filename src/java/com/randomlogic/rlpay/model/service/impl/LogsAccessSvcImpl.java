/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import com.randomlogic.rlpay.application.monitor.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.domain.interfaces.ILogs;
import com.randomlogic.rlpay.model.service.dao.HibernateDAOSvc;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
////@Service
public class LogsAccessSvcImpl extends HibernateDAOSvc implements ILogsAccessSvc
{
    public LogsAccessSvcImpl()
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
////            session.createSQLQuery ("TRUNCATE Logs").executeUpdate();
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
        Logs logs = null;
        NativeQuery result;
        List<Object[]> resultSet;
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");

        initSession();

        if (session!= null)
        {
            logs = (Logs)object;
            /**
             * SELECT *
             *      FROM [dbo].[logs]
             *      WHERE customerId LIKE '%289362%'
             *      AND errorMsg LIKE '%Successful%'
             *      AND logDate >= '2019-04-23 00:00:00'
             *      AND logDate <= '2019-04-23 23:59:59.999'
             */
            String query = "SELECT * FROM logs" +
                           " WHERE customerId LIKE '%" + logs.getCustomerId() + "%'" +
                           " AND transactionId LIKE '%" + logs.getTransactionId() + "%'" +
                           " AND errorCode LIKE '%" + logs.getErrorCode() + "%'" +
                           " AND errorMsg LIKE '%" + logs.getErrorMsg() + "%'" +
                           " AND amount LIKE '%" + logs.getAmount()  + "%'";
            try
            {
                result = session.createSQLQuery (query);
                resultSet = (List<Object[]>)result.list();

                if (!resultSet.isEmpty())
                {
                    for (Object[] element : resultSet)
                    {
                        logs.setIdLogs (Integer.parseInt (element[0].toString()));
                        logs.setUsers (Integer.parseInt (element[1].toString()));
                        logs.setClientip (element[2].toString());
                        logs.setTransactionId (element[3].toString());
                        logs.setAuthCode (element[4].toString());
                        logs.setAmount (element[5].toString());
                        logs.setCustomerId (element[6].toString());
                        logs.setErrorCode (element[7].toString());
                        logs.setErrorType (element[8].toString());
                        logs.setErrorMsg (element[9].toString());
                        logs.setLogText (element[10].toString());
                        logs.setErrorSource (element[11].toString());
                        logs.setLogDate (format.parse (element[12].toString()));
                    }
                }
                else
                {
                    logs = null;
                }
            }
            catch (Exception e)
            {
                logs = null;
                Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (LogsAccessSvcImpl.class, "Logs.find failed with a NULL session.");
        }

        return logs;
    }

    @Override
    public boolean persist (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        Logs newLog;
        Iterator iterator;
        Collection<Logs> logs;

        if (object != null)//validate
        {
            logs = (Collection<Logs>) object;
            initSession();

            if (session!= null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = logs.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newLog = (Logs)iterator.next();
                        session.persist (newLog);
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
                            Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (LogsAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (LogsAccessSvcImpl.class, "Logs.persist failed with a NULL session.");
            }
        }
        else
        {
            Logger.log (LogsAccessSvcImpl.class, "Logs.persist failed with a NULL object.");
            result = false;
        }

        return result;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean save (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction = null;
        Logs newLog;
        Iterator iterator;
        Collection<Logs> logs;

        if (object != null)//validate
        {
            logs = (Collection<Logs>)object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();

                    iterator = logs.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newLog = (Logs)iterator.next();
                        session.save (newLog);
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
                            Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
                            transaction.rollback();
                        }
                        catch (Exception ex)
                        {
                            Logger.log (LogsAccessSvcImpl.class, "Rollback() failed");
                        }
                    }
                }
            }
            else
            {
                Logger.log (LogsAccessSvcImpl.class, "Logs.save failed with a NULL session.");
                result = false;
            }
        }
        else
        {
            Logger.log (LogsAccessSvcImpl.class, "Logs.save failed with a NULL object.");
            result = false;
        }

        return result;
    }

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public Logs readLog (String customerId)
    {
        Logs log = new Logs();

        log.setCustomerId (customerId);
        return (Logs)find (log);
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<Logs> readLogs()
    {
        Collection<Logs> logs = new ArrayList<Logs>();
        Query query;
        Iterator<Logs> iterator;

        initSession();

        if (session != null)
        {
            try
            {
                query = session.createQuery ("from Logs");
                iterator = (Iterator<Logs>)query.iterate();

                while (iterator.hasNext())
                {
                    logs.add (iterator.next());
                }
            }
            catch (Exception e)
            {
                Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (LogsAccessSvcImpl.class, "Logs.readLogs failed with a NULL session.");
        }

        return logs;
    }

    @Override
    public boolean removeLog (ILogs log)
    {
        return true;
    }

    @Override
    public boolean updateLog (ILogs log, boolean isUpdate)
    {
        boolean result;
        Collection<Logs> logs = new ArrayList<Logs>();

        if (isUpdate)
        {
            // TODO: complete update code later
            result = true;
        }
        else
        {
            logs.add ((Logs)log);
            result = merge (logs);
        }

        return result;
    }

    @Override
    public boolean merge (Collection<?> object)
    {
        boolean result = true;
        Transaction transaction;
        Logs newLog;
        Iterator iterator;
        Collection<Logs> logs;

        if (object != null)//validate
        {
            logs = (Collection<Logs>) object;
            initSession();

            if (session != null)
            {
                try
                {
                    transaction = session.beginTransaction();
                    iterator = logs.iterator();

                    while (iterator.hasNext() && result)
                    {
                        newLog = (Logs)iterator.next();
                        session.merge (newLog);
                    }

                    transaction.commit();
                }
                catch (Exception e)
                {
                    Logger.log (Logger.EXCEPTION, APIUserAccessSvcImpl.class, e);
                    result = false;
                }
            }
            else
            {
                Logger.log (LogsAccessSvcImpl.class, "Logs.merge failed with a NULL session.");
                result = false;
            }
        }
        // else do nothing

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

    @Override
    public Collection<Logs> searchLogs (ILogs object, Date begin, Date end)
    {
        Logs logs = null;
        Collection<Logs> logList = new ArrayList<Logs>();
        NativeQuery result;
        List<Object[]> resultSet;
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = "";

        // Format correct date search parameters, compensate for date entry errors
        if (!(begin == null || end == null))
        {
            if (begin.before (end))
            {
                dateString = " AND logDate >= '" + format.format (begin)   + "'" +
                             " AND logDate <= '" + format.format (end) + "'";
            }
            else if (begin == end || end.before (begin))
            {
                dateString = " AND logDate = '" + format.format (begin)   + "'";
            }
            else
            {
                dateString = " AND logDate = '" + format.format (end)   + "'";
            }
        }
        else
        {
            dateString = "";
        }

        initSession();

        if (session!= null)
        {
            logs = (Logs)object;
            /**
             * SELECT *
             *      FROM [dbo].[logs]
             *      WHERE customerId LIKE '%289362%'
             *      AND errorMsg LIKE '%Successful%'
             *      AND logDate >= '2019-04-23 00:00:00'
             *      AND logDate <= '2019-04-23 23:59:59.999'
             */
            String query = "SELECT * FROM logs" +
                           " WHERE customerId LIKE '%" + logs.getCustomerId() + "%'" +
                           " AND transactionId LIKE '%" + logs.getTransactionId() + "%'" +
                           " AND errorCode LIKE '%" + logs.getErrorCode() + "%'" +
                           " AND errorMsg LIKE '%" + logs.getErrorMsg() + "%'" +
                           " AND amount LIKE '%" + logs.getAmount()  + "%'" +
                           dateString;
            try
            {
                result = session.createSQLQuery (query);
                resultSet = (List<Object[]>)result.list();

                if (!resultSet.isEmpty())
                {
                    for (Object[] element : resultSet)
                    {
                        logs = new Logs();
                        logs.setIdLogs (Integer.parseInt (element[0].toString()));
                        logs.setUsers (Integer.parseInt (element[1].toString()));
                        logs.setClientip (element[2].toString());
                        logs.setTransactionId (element[3].toString());
                        logs.setAuthCode (element[4].toString());
                        logs.setAmount (element[5].toString());
                        logs.setCustomerId (element[6].toString());
                        logs.setErrorCode (element[7].toString());
                        logs.setErrorType (element[8].toString());
                        logs.setErrorMsg (element[9].toString());
                        logs.setLogText (element[10].toString());
                        logs.setErrorSource (element[11].toString());
                        logs.setLogDate (format.parse (element[12].toString()));
                        logList.add (logs);
                    }
                }
                // else do nothing
            }
            catch (Exception e)
            {
                logList = new ArrayList<Logs>();
                Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
            }
        }
        else
        {
            Logger.log (LogsAccessSvcImpl.class, "Logs.search failed with a NULL session.");
        }

        return logList;
    }
}
