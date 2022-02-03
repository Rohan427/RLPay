/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.company.entity.User;
import com.randomlogic.rlpay.model.service.impl.LogsAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;

/**
 *
 * @author Paul.Allen
 */
public class Logger
{
    public final static int ERROR = 0;
    public final static int INFO = 1;
    public final static int VERBOSE = 2;
    public final static int DEBUG = 3;
    public final static int EXCEPTION = 5;
    public final static int MOD = 10;

    public final static int GLB_DEBUG = DEBUG;

    private static User user = new User();
    private static Collection<Logs> logs = new ArrayList<Logs>();

    private static String clientIP = "0.0.0.0";


    public static void setUser (User newUser)
    {
        user = newUser;
    }

    public static void log (Class caller, int code, String message)
    {
        java.util.Date utilDate = Calendar.getInstance().getTime();

        debug (Logger.class, GLB_DEBUG, "<" + utilDate + "> " + caller.toString() +
               ", Error Code: " + code +
               ", Message: " + message
               );
        // else do nothing
    }

    public static void log (Class caller, String message)
    {
        java.util.Date utilDate = Calendar.getInstance().getTime();

        debug (Logger.class, GLB_DEBUG, "<" + utilDate + "> " + caller.toString() +
               ", Message: " + message
              );
        // else do nothing
    }

    /**
     *
     * @param level  the minimum level at which to report
     * @param caller the reporting class
     * @param newLog the data to report
     * @param record the true - record in database, false do not record
     */
    public static synchronized void log (int level, Class caller, Logs newLog, boolean record)
    {
        java.util.Date utilDate = Calendar.getInstance().getTime();
        ILogsAccessSvc logSvc = new LogsAccessSvcImpl();

        if (level == MOD || level <= INFO || level <= GLB_DEBUG)
        {
            if (!newLog.getClientip().equals ("0.0.0.0"))
            {
                Logger.clientIP = newLog.getClientip();
            }
            else
            {
                newLog.setClientip (Logger.clientIP);
            }

            if (newLog.getAuthCode() == null)
            {
                newLog.setAuthCode ("none");
            }
            //else do nothing

            newLog.setUsers (user.getId().getIdUser());

            System.out.println ("<" + utilDate + "> \n\t" +
                                caller.toString() +
                                "\n\tClient IP:      " + Logger.clientIP +
                                "\n\tMethod:         " + newLog.getMethod() +
                                "\n\tCommand:        " + newLog.getCommand() +
                                "\n\tTransaction ID: " + newLog.getTransactionId() +
                                "\n\tBatch ID:       " + newLog.getBatchId() +
                                "\n\tAuth Code:      " + newLog.getAuthCode() +
                                "\n\tAmount:         " + newLog.getAmount() +
                                "\n\tCustomer ID:    " + newLog.getCustomerId() +
                                "\n\tError Code:     " + newLog.getErrorCode() +
                                "\n\tError Msg:      " + newLog.getErrorMsg() +
                                "\n\tDetails:        " + newLog.getLogText()
                               );

            // Create log entry in DB if flag set

            if (record)
            {
                logs.add (newLog);
            }
            // else do nothing - record flag not set

            if (!logSvc.save (logs))
            {
                logSvc.close();

                logSvc = new LogsAccessSvcImpl();

                if (!logSvc.save (logs))
                {
                    System.err.println ("<" + utilDate + "> Failed to create Logs DB entry: \n\t" + caller.toString());
                }
                //else do nothing
            }
            // else do nothing
        } // ENDIF: if (level == MOD || level >= INFO)
        // else do nothing
    }

    /**
     *
     * @param level
     * @param caller
     * @param e
     */
    public static void log (int level, Class caller, Exception e)
    {
        java.util.Date utilDate = Calendar.getInstance().getTime();

        if (level == EXCEPTION || level == MOD || level <= GLB_DEBUG)
        {
            System.err.println ("<" + utilDate + "> " + caller.toString() + " threw an exception:\n");
            System.err.println (e.getLocalizedMessage() + ":");
            System.err.println (Arrays.toString (e.getStackTrace()) + "\n");
        }
        // else do nothing
    }

    public static void setClientIP (String clientIP)
    {
        Logger.clientIP = clientIP;
    }

    /**
     *
     * @param level
     * @param caller
     * @param e
     * @param message
     */
    public static void log (int level, Class caller, Exception e, String message)
    {
        java.util.Date utilDate = Calendar.getInstance().getTime();

        if (level == MOD || level <= GLB_DEBUG)
        {
            System.err.println ("<" + utilDate + "> " + caller.toString() + message);
            System.err.println (e.getLocalizedMessage());
            System.err.println (Arrays.toString (e.getStackTrace()));
        }
        // else do nothing
    }

    /**
     *
     * @param caller
     * @param debug
     * @param message
     */
    public static void debug (Class caller, int debug, String message)
    {
        if (debug <= GLB_DEBUG || debug == MOD)
        {
            System.err.println (caller.toString() + ": " + message);
        }
        // else do nothing
    }
}
