/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.manager;

import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.LogData;
import com.randomlogic.rlpay.application.monitor.Logger;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.ILogs;
import com.randomlogic.rlpay.model.service.impl.LogsAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;
import org.apache.commons.validator.routines.DateValidator;
import com.randomlogic.rlpay.application.util.ServletParams;

/**
 *
 * @author Paul.Allen
 */
public class LogManager extends HttpServlet
{
    private ServletParams params;
    private String resultPage = null;
    private User user = null;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest (request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest (request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo ()
    {
        return "Short description";
    }// </editor-fold>

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     *
     * @param newrequest
     * @param newresponse
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest (HttpServletRequest newrequest, HttpServletResponse newresponse)
            throws ServletException, IOException
    {
        params = new ServletParams (new ErrorBean(), getServletConfig().getServletContext(), newrequest, newresponse);
        user = (User)params.getRequest().getSession().getAttribute ("userBean");

        switch (params.getRequest().getParameter ("mode"))
        {
            case "search":
                search();
                break;

            default:
                badRequest();
                break;
        }
    }

    private void search() throws ServletException, IOException
    {
        DateValidator dateValidator = DateValidator.getInstance();
        Date begin;
        Date end;
        String beginParm = params.getRequest().getParameter ("beginDate");
        String endParm = params.getRequest().getParameter ("endDate");
        ILogsAccessSvc logAccess = new LogsAccessSvcImpl();
        ILogs searchLog;
        Collection<Logs> logList;
        resultPage = "/admin/logs.jsp";
        LogData logData = new LogData();

        // For input type datetime-local which has a "T" between date and time
        if (beginParm != null)
        {
            beginParm = beginParm.replace ("T", " ");
        }
        // else do nothing

        if (endParm != null)
        {
            endParm = endParm.replace ("T", " ");
        }
        // else do nothing

        begin = dateValidator.validate (beginParm, "yyyy-MM-dd HH:mm");

        dateValidator = DateValidator.getInstance();
        end = dateValidator.validate (endParm, "yyyy-MM-dd HH:mm");

        if (begin == null)
        {
            // Hardcoded for the earliest date a log would have occured
            SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");

            try
            {
                begin = format.parse ("2019-04-23 00:00:00.000");
            }
            catch (ParseException ex)
            {
                begin = Calendar.getInstance().getTime();
            }
        }
        //else do nothing

        if (end == null)
        {
            end = Calendar.getInstance().getTime();
        }
        // else do nothing

        logData.setUsers (1);
        logData.setClientip ("");
        logData.setTransactionId (params.getRequest().getParameter ("transId"));
        logData.setAuthCode ("");
        logData.setAmount ("");
        logData.setCustomerId (params.getRequest().getParameter ("account"));
        logData.setErrorCode (params.getRequest().getParameter ("errorCode"));
        logData.setErrorType ("");
        logData.setErrorMsg ("");
        logData.setLogText ("");
        logData.setErrorSource ("");
        logData.setLogDate (Calendar.getInstance().getTime());

        searchLog = new Logs (logData);

        logList = logAccess.searchLogs (searchLog, begin, end);
        params.getRequest().getSession().setAttribute ("logList", logList);

        Logger.log (LogManager.class, "LogManager Search\n\n" + params.getUri());

        try
        {
            params.getContext().getRequestDispatcher (resultPage).forward (params.getRequest(), params.getResponse());
        }
        catch (ServletException | IOException ex)
        {
            Logger.log (Logger.EXCEPTION, LogManager.class, ex, "LogManager: Exception.\n");

            if (params.getError() != null)
            {
                params.getRequest().getSession().removeAttribute ("errorMsg");
                params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
                params.getError().generateError (params.getContext(),
                                                 params.getRequest(),
                                                 params.getResponse(),
                                                 "Request",
                                                 params.getUri(),
                                                 ErrorBean.ERROR_INVALID_URI,
                                                 "Requested page not found",
                                                 "/genError.jsp",
                                                 ex.getMessage(),
                                                 ex, null
                                                );
            }
        }
    }

    private void badRequest() throws ServletException, IOException
    {
        Logger.log (LogManager.class, "Logs: Bad Request.");

        if (params.getErrorBean() != null)
        {
            params.getRequest().getSession().removeAttribute ("errorMsg");
            params.getRequest().getSession().setAttribute ("errorMsg", params.getErrorBean());
            params.getErrorBean().generateError (params.getContext(),
                                                 params.getRequest(),
                                                 params.getResponse(),
                                                 "Request",
                                                 params.getUri(),
                                                 ErrorBean.ERROR_INVALID_URI,
                                                 "Requested page not found",
                                                 "/genError.jsp",
                                                 null,
                                                 null, null
                                                );
        }
    }
}
