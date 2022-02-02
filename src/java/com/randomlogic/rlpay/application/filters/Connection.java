/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import com.randomlogic.rlpay.application.util.ServletParams;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.company.service.impl.SSO;
import com.randomlogic.rlpay.model.domain.company.service.interfaces.ISSO;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;
import static com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureAPICodes.AZURE_AUTH_REDIRECT;
import com.randomlogic.rlpay.resources.api.azure.model.domain.entity.AzureError;
import com.randomlogic.rlpay.resources.api.azure.model.domain.interfaces.IAuthResponse;

/**
 *
 * @author Paul.Allen
 */
public class Connection implements Filter
{
    private static int DEBUG = Logger.MOD;
    String clientIP = "0.0.0.0";

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;

    public Connection()
    {
    }

    private void doBeforeProcessing (ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
        Logger.debug (Connection.class, DEBUG, "\nConnection:DoBeforeProcessing()");

        Logs log = new Logs (0,
                             1,
                             "0.0.0.0",
                             "0",
                             "none",
                             "0.00",
                             "0",
                             "" + API_SERVER_CLIENT_CONN,
                             "" + TRANS_REQUEST,
                             "" + TRANS_CLIENT_OPTION,
                             "" + TRANS_CLIENT_OPTION,
                             Connection.class.toString(),
                             Calendar.getInstance().getTime()
                            );

        Logger.log (Logger.VERBOSE, Connection.class, log, false);
    }

    private void doAfterProcessing (ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
        HttpServletResponse servResponse = (HttpServletResponse)response;

        Logger.debug (Connection.class, DEBUG, "Connection:DoAfterProcessing()");

        servResponse.setHeader ("Access-Control-Allow-Origin", "*");
        servResponse.setStatus (HttpServletResponse.SC_OK);
        servResponse.setHeader ("Access-Control-Allow-Methods", "POST,OPTIONS");
        servResponse.setHeader ("Access-Control-Allow-Headers", "x-requested-with,cache-control,content-type,origin,method,SOAPAction");
    }

    /**
     *
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     *
     * @exception IOException      if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter (ServletRequest request,
                          ServletResponse response,
                          FilterChain chain
                         )
            throws IOException, ServletException
    {
        RespWrapper wrappedResp;
        HttpServletRequest httpReq = (HttpServletRequest)request;
        String method = httpReq.getMethod();
        Throwable problem = null;
        boolean ssoResult = true;

        Logger.debug (Connection.class, DEBUG, "Connection:doFilter() method: " + method);

        clientIP = getClientIp (httpReq);
        Logger.setClientIP (clientIP);

        if (method.equals ("OPTIONS"))
        {
            Logs log = new Logs (0,
                                 1,
                                 "0.0.0.0",
                                 "0",
                                 "none",
                                 "0.00",
                                 "0",
                                 "" + API_SERVER_CLIENT_CONN,
                                 "" + TRANS_REQUEST,
                                 "" + TRANS_CLIENT_OPTION,
                                 "" + TRANS_CLIENT_OPTION,
                                 Connection.class.toString(),
                                 Calendar.getInstance().getTime()
                                );

            Logger.log (Logger.VERBOSE, Connection.class, log, false);

            doBeforeProcessing (request, response);
            wrappedResp = new RespWrapper ((HttpServletResponse)response);
            doAfterProcessing (request, wrappedResp);
            response.getOutputStream().write (wrappedResp.getWrapperBytes());
        }
        else
        {
            Logger.debug (Connection.class, DEBUG, "POST,GET");

            try
            {
                if (method.equals ("POST"))
                {
                    // Verify SSO authentication
                    ISSO sso = new SSO();
                    ServletParams params = new ServletParams (new ErrorBean(), request.getServletContext(), httpReq, (HttpServletResponse)response);

                    sso.init (params);

                    // Check SSO authentication if enabled
                    if (sso.isEnabled())
                    {
                        ssoResult = sso.authenticate();
                    }
                    else
                    {
                        ssoResult = true;
                    }

                    IAuthResponse ssoResponse = sso.getResponse();

                    if (ssoResult)
                    {
                        Logger.debug (Connection.class, DEBUG, "Connection: SSO Authenticated");
                    }
                    else
                    {
                        if (((AzureError)ssoResponse.getErrorObject()).getErrorCode() == AZURE_AUTH_REDIRECT)
                        {
                            Logger.debug (Connection.class, DEBUG, "Connection: SSO Redirect");

                            ((HttpServletResponse)response).sendRedirect (ssoResponse.getRedirectUrl());
                        }
                        else
                        {
                            // TODO: report error?
                        }
                    }
                }
                else
                {
                    // TODO: What if it's not a POST?
                }

                chain.doFilter (request, response);
            }
            catch (Throwable t)
            {
                // If an exception is thrown somewhere down the filter chain,
                // we still want to execute ours after processing, and then
                // rethrow the problem after that.
                problem = t;
            }
        }

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null)
        {
            if (problem instanceof ServletException)
            {
                throw (ServletException) problem;
            }
            // else do nothing

            if (problem instanceof IOException)
            {
                throw (IOException) problem;
            }
            // else do nothing

            sendProcessingError (problem, response);
        }
    }

    private static String getClientIp (HttpServletRequest request)
    {
        String remoteAddr = "";

        if (request != null)
        {
            remoteAddr = request.getHeader ("X-FORWARDED-FOR");

            if (remoteAddr == null || "".equals (remoteAddr))
            {
                remoteAddr = request.getRemoteAddr();
            }
            // else do nothing
        }
        // else do nothing

        return remoteAddr;
    }

    /**
     * Return the filter configuration object for this filter.
     * @return
     */
    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig (FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy()
    {
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init (FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;

        if (filterConfig != null)
        {
            Logger.debug (Connection.class, DEBUG, "Connection: Initializing filter");
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString()
    {
        if (filterConfig == null)
        {
            return ("Connection()");
        }

        StringBuilder sb = new StringBuilder ("Connection(");
        sb.append (filterConfig);
        sb.append (")");
        return (sb.toString());
    }

    private void sendProcessingError (Throwable t, ServletResponse response)
    {
        String stackTrace = getStackTrace (t);
        String logTrace;
        Logs log = new Logs (Logger.VERBOSE,
                             1,
                             "0.0.0.0",
                             "0",
                             "none",
                             "0.00",
                             "0",
                             "" + API_SERVER_CLIENT_RESP,
                             INTERNAL_ERROR,
                             API_RESOURCE_ERROR,
                             "",
                             Connection.class.toString(),
                             Calendar.getInstance().getTime()
                            );

        try
        {
            logTrace = stackTrace.substring (0, 1023);
        }
        catch (IndexOutOfBoundsException e)
        {
            logTrace = stackTrace;
        }

        if (stackTrace != null && !stackTrace.equals (""))
        {
            log.setLogText (logTrace);
            Logger.log (Logger.ERROR, Connection.class, log, true);

            try
            {
                response.setContentType ("text/xml");
                PrintStream ps = new PrintStream (response.getOutputStream());
                PrintWriter pw = new PrintWriter (ps);

                pw.print ("<?xml version='1.0' encoding='UTF-8'?>" +
                          "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                              "<S:Body>" +
                                    "<ns0:paymentResponse xmlns:ns0=\"http://facade.payment.portal.rlpay.randomlogic.com/\">" +
                                      "<return>" +
                                          "<authCode>none</authCode>" +
                                              "<custAccount>0</custAccount>" +
                                              "<errorCode>" + API_SERVER_RESOURCE + "</errorCode>" +
                                              "<errorMsg>" + API_RESOURCE_ERROR + "</errorMsg>" +
                                              "<guid>0</guid>" +
                                              "<profileToken>0</profileToken>" +
                                              "<transactionId>0</transactionId>" +
                                      "</return>" +
                                  "</ns0:paymentResponse>" +
                              "</S:Body>" +
                          "</S:Envelope>"
                         );
                pw.close ();
                ps.close ();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
                Logger.debug (Connection.class, DEBUG, "sendProcessingError() exception1");

                stackTrace = getStackTrace (t);

                try
                {
                    logTrace = stackTrace.substring (0, 1023);
                }
                catch (IndexOutOfBoundsException e)
                {
                    logTrace = stackTrace;
                }

                log.setLogText (logTrace);
                Logger.log (Logger.ERROR, Connection.class, log, true);
            }
        }
        else
        {
            log.setErrorCode ("" + API_SERVER_OUT_ERROR);
            log.setErrorMsg (API_RESOURCE_ERROR);
            log.setLogText (t.getLocalizedMessage());
            Logger.log (Logger.ERROR, Connection.class, log, true);

            try
            {
                response.setContentType ("text/xml");
                PrintStream ps = new PrintStream (response.getOutputStream());
                PrintWriter pw = new PrintWriter (ps);

                pw.print ("<?xml version='1.0' encoding='UTF-8'?>" +
                          "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                              "<S:Body>" +
                                  "<ns0:paymentResponse xmlns:ns0=\"http://service.portal/\">" +
                                      "<return>" +
                                          "<authCode>none</authCode>" +
                                              "<custAccount>0</custAccount>" +
                                              "<errorCode>" + API_SERVER_OUT_ERROR + "</errorCode>" +
                                              "<errorMsg>" + t.getLocalizedMessage() + "</errorMsg>" +
                                              "<guid>0</guid>" +
                                              "<profileToken>0</profileToken>" +
                                              "<transactionId>0</transactionId>" +
                                      "</return>" +
                                  "</ns0:paymentResponse>" +
                              "</S:Body>" +
                          "</S:Envelope>"
                         );
                pw.close ();
                ps.close ();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
                Logger.debug (Connection.class, DEBUG, "sendProcessingError() exception2");
            }
        }
    }

    public static String getStackTrace (Throwable t)
    {
        String stackTrace;

        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter (sw);
            t.printStackTrace (pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        }
        catch (Exception ex)
        {
            Logger.debug (Connection.class, DEBUG, "getStackTrace() exception1");

            stackTrace = "Failed to get stack trace";
        }

        return stackTrace;
    }

    public void log (String msg)
    {
        filterConfig.getServletContext().log (msg);
    }
}
