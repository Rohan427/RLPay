/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.monitor;

import java.beans.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pallen
 */
public class ErrorBean implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final String PROP_CALLER_PROPERTY = "callingPage";
    private String callingPage = "None";

    public static final String PROP_OPERATION_PROPERTY = "operation";
    private String operation = "None";

    public static final String PROP_ERRORTYPE_PROPERTY = "errorType";
    private int errorType = ERROR_NONE;

    public static final String PROP_ERRORMESSAGE_PROPERTY = "errorMessage";
    private String errorMessage = "None";

    public static final String PROP_STACKTRACE_PROPERTY = "stackTrace";
    private String stackTrace = "None";

    public static final String PROP_SQLERROR_PROPERTY = "sqlError";
    private String sqlError = "None";

    public static final String PROP_JAVAEXECPTION_PROPERTY = "javaException";
    private String javaException = "None";

    private PropertyChangeSupport propertySupport;

    // Error type codes
    public static final int ERROR_NONE                  = 0;
    public static final int ERROR_AUTH                  = 1;
    public static final int ERROR_SESSION_TIMEOUT       = 2;
    public static final int ERROR_INVALID_INPUT         = 3;
    public static final int ERROR_SQL_ERROR             = 4;
    public static final int ERROR_JAVA_EXCEPTION        = 5;
    public static final int ERROR_ATTR_NOT_SET          = 6;
    public static final int ERROR_GENERAL_ERROR         = 7;
    public static final int ERROR_NULLPTR_EXCEPTION     = 8;
    public static final int ERROR_SQL_NULLPTR           = 9;
    public static final int ERROR_SQL_EXCEPTION         = 10;
    public static final int ERROR_FILE_UL_EXCEPTION     = 11;
    public static final int ERROR_INVALID_FILE_TYPE     = 12;
    public static final int ERROR_FILE_LNTH_MISMATCH    = 13;
    public static final int ERROR_UNKOWN_DB_ERROR       = 14;
    public static final int ERROR_NOT_SUPPORTED         = 15;
    public static final int ERROR_INVALID_URI           = 16;
    public static final int ERROR_NUMFORMAT_EXCEPTION   = 17;
    public static final int ERROR_GENERAL_EXCEPTION     = 18;
    public static final int ERROR_NO_RECORDS_FOUND      = 19;
    public static final int ERROR_PARSE_EXCEPTION       = 20;
    public static final int ERROR_MAX                   = ERROR_PARSE_EXCEPTION + 1;

    public static String[] errorStrings =
    {
           "ERROR_NONE",
           "ERROR_AUTH",
           "ERROR_SESSION_TIMEOUT",
           "ERROR_INVALID_INPUT",
           "ERROR_SQL_ERROR",
           "ERROR_JAVA_EXCEPTION",
           "ERROR_ATTR_NOT_SET",
           "ERROR_GENERAL_ERROR",
           "ERROR_NULLPTR_EXCEPTION",
           "ERROR_SQL_NULLPTR",
           "ERROR_SQL_EXCEPTION",
           "ERROR_FILE_UL_EXCEPTION",
           "ERROR_INVALID_FILE_TYPE",
           "ERROR_FILE_LNTH_MISMATCH",
           "ERROR_UNKOWN_DB_ERROR",
           "ERROR_NOT_SUPPORTED",
           "ERROR_INVALID_URI",
           "ERROR_NUMFORMAT_EXCEPTION",
           "ERROR_GENERAL_EXCEPTION",
           "ERROR_NO_RECORDS_FOUND",
           "ERROR_PARSE_EXCEPTION"
    };

    public ErrorBean()
    {
        propertySupport = new PropertyChangeSupport (this);
    }

    public String getCallingPage()
    {
        return callingPage;
    }

    public void setCallingPage (String value)
    {
        String oldValue = callingPage;
        callingPage = value;
        propertySupport.firePropertyChange (PROP_CALLER_PROPERTY, oldValue, callingPage);
    }

    public String getOperation()
    {
        return operation;
    }

    public void setOperation (String value)
    {
        String oldValue = operation;
        operation = value;
        propertySupport.firePropertyChange (PROP_OPERATION_PROPERTY, oldValue, operation);
    }

    public int getErrorType()
    {
        return errorType;
    }

    public void setErrorType (int value)
    {
        int oldValue = errorType;
        errorType = value;
        propertySupport.firePropertyChange (PROP_ERRORTYPE_PROPERTY, oldValue, errorType);
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public String getErrorStrings()
    {
        return errorStrings[errorType];
    }

    public void setErrorMessage (String value)
    {
        String oldValue = errorMessage;
        errorMessage = value;
        propertySupport.firePropertyChange (PROP_ERRORMESSAGE_PROPERTY, oldValue, errorMessage);
    }

    public String getStackTrace()
    {
        return stackTrace;
    }

    public void setStackTrace (String value)
    {
        String oldValue = stackTrace;
        stackTrace = value;
        propertySupport.firePropertyChange (PROP_STACKTRACE_PROPERTY, oldValue, stackTrace);
    }

    public String getSqlError()
    {
        return sqlError;
    }

    public void setSqlError (String value)
    {
        String oldValue = sqlError;
        sqlError = value;
        propertySupport.firePropertyChange (PROP_SQLERROR_PROPERTY, oldValue, sqlError);
    }

    public String getJavaException()
    {
        return javaException;
    }

    public void setJavaException (String value)
    {
        String oldValue = javaException;
        javaException = value;
        propertySupport.firePropertyChange (PROP_JAVAEXECPTION_PROPERTY, oldValue, javaException);
    }

    public void addPropertyChangeListener (PropertyChangeListener listener)
    {
        propertySupport.addPropertyChangeListener (listener);
    }

    public void removePropertyChangeListener (PropertyChangeListener listener)
    {
        propertySupport.removePropertyChangeListener (listener);
    }

    public void generateError (ServletContext context,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               String operation,
                               String uri,
                               int errType,
                               String message,
                               String errorPath,
                               String exception,
                               Exception ex,
                               String sqlexception) throws ServletException, IOException
    {
        setCallingPage (uri);
        setOperation (operation);
        setErrorType (errType);
        setErrorMessage (message);
        setJavaException (exception);

        if (ex != null)
        {
            setStackTraceAsString (ex);
        }

        setSqlError (sqlexception);
        context.getRequestDispatcher (errorPath).forward (request, response);
    }

    public void reportError (ServletContext context,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             String errorPath
                            ) throws ServletException, IOException
    {
        context.getRequestDispatcher (errorPath).forward (request, response);
    }

    public void reset()
    {
        setCallingPage ("None");
        setOperation ("None");
        setErrorType (ERROR_NONE);
        setErrorMessage ("None");
        setJavaException ("None");
        setStackTrace ("None");
        setSqlError ("None");
    }

    @SuppressWarnings("empty-statement")
    public void setStackTraceAsString (Exception exc)
    {
        String localstackTrace = "*** Error in getStackTraceAsString()";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream (baos);
        exc.printStackTrace (ps);

        try
        {
            localstackTrace = baos.toString ("UTF8"); // charsetName e.g. ISO-8859-1
            ps.close();

            try
            {
                baos.close();
            }
            catch(Exception ex)
            {
                localstackTrace = "*** Error in getStackTraceAsString()";
            }
        }
        catch (Exception ex)
        {
            ;
        }

        setStackTrace (localstackTrace);
    }
}
