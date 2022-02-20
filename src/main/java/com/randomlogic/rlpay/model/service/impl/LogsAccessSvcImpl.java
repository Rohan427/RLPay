/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.service.impl;

import com.randomlogic.rlpay.application.config.RLPayConstants;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.IDomainObject;
import com.randomlogic.rlpay.model.domain.interfaces.ILogs;
import com.randomlogic.rlpay.model.service.interfaces.ILogSvcDAO;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Service ("logSvc")
public class LogsAccessSvcImpl implements ILogsAccessSvc
{
    @Autowired (required = true)
    ILogSvcDAO logDao;

    /**
     *
     * @param type
     * @return
     */
    @Override
    public boolean delete (String type)
    {
        boolean result = false;

        return result;
    }

    @Override
    public IDomainObject find (IDomainObject object)
    {
        Logs logs = null;
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");


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
//        try
//        {
//            logs = logDao.findLogsWithParms (logs.getCustomerId(), logs.getTransactionId(),
//                                              logs.getErrorCode(), logs.getErrorMsg(),
//                                              logs.getAmount()
//                                             );
//        }
//        catch (Exception e)
//        {
//            logs = null;
//            Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
//        }

        return logs;
    }

    @Override
    public boolean persist (Collection<?> object)
    {
        boolean result = true;

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
        Logs newLog;
        Iterator iterator;
        Collection<Logs> logs;

        if (object != null)//validate
        {
            logs = (Collection<Logs>)object;


            try
            {

                iterator = logs.iterator();

                while (iterator.hasNext() && result)
                {
                    newLog = (Logs)iterator.next();
                    newLog.setIdLogs (null);

                    // Validate uncontrolled content
                    if (newLog.getErrorSource().length () > RLPayConstants.LOG_ERR_SRC_SIZE)
                    {
                        newLog.setErrorSource (newLog.getErrorSource().substring (0, RLPayConstants.LOG_ERR_SRC_SIZE - 1));
                    }
                    // else do nothing

                    if (newLog.getLogText().length () > RLPayConstants.LOG_TXT_SIZE)
                    {
                        newLog.setLogText (newLog.getLogText().substring (0, (RLPayConstants.LOG_TXT_SIZE - 1)));
                    }
                    // else do nothing

                    logDao.save (newLog);
                }
            }
            catch (Exception e)
            {
                result = false;

                Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
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

        try
        {
            logDao.findAll().forEach (logs::add);
        }
        catch (Exception e)
        {
            Logger.log (Logger.EXCEPTION, LogsAccessSvcImpl.class, e);
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
        boolean result = false;
        Collection<Logs> logs = new ArrayList<Logs>();

        if (isUpdate)
        {
            if(logDao.existsById (log.getIdLogs()))
            {
                log = logDao.save ((Logs)log);
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean merge (Collection<?> object)
    {
        boolean result = true;

        return result;
    }

    @Override
    public void close()
    {

    }

    @Override
    public Collection<Logs> searchLogs (ILogs object, Date begin, Date end)
    {
        Logs logs = (Logs)object;
        Collection<Logs> logList = new ArrayList<Logs>();
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
        String dateString = "";
        String beginString;
        String endString;

        // Format correct date search parameters, compensate for date entry errors
////        if (!(begin == null || end == null))
////        {
////            if (begin.before (end))
////            {
////////                dateString = " AND logDate >= '" + format.format (begin)   + "'" +
////////                             " AND logDate <= '" + format.format (end) + "'";
////                beginString = format.format (begin);
////                endString = format.format (end);
////
////                logList = logDao.searchLogsBetween (logs.getCustomerId(), logs.getTransactionId(),
////                                                     logs.getErrorCode(), logs.getErrorMsg(),
////                                                     logs.getAmount(), beginString, endString
////                                                    );
////
////            }
////            else if (begin == end || end.before (begin))
////            {
////////                dateString = " AND logDate = '" + format.format (begin)   + "'";
////                beginString = format.format (begin);
////                logList = logDao.searchLogsByDate (logs.getCustomerId(), logs.getTransactionId(),
////                                                    logs.getErrorCode(), logs.getErrorMsg(),
////                                                    logs.getAmount(), beginString
////                                                   );
////            }
////            else
////            {
////////                dateString = " AND logDate = '" + format.format (end)   + "'";
////                endString = format.format (end);
////                logList = logDao.searchLogsByDate (logs.getCustomerId(), logs.getTransactionId(),
////                                                    logs.getErrorCode(), logs.getErrorMsg(),
////                                                    logs.getAmount(), endString
////                                                   );
////            }
////        }
////        else
////        {
////            logList = logDao.searchLogs (logs.getCustomerId(), logs.getTransactionId(),
////                                          logs.getErrorCode(), logs.getErrorMsg(),
////                                          logs.getAmount()
////                                         );
////        }

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

        return logList;
    }
}
