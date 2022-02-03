/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.impl;

import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.LogData;
import com.randomlogic.rlpay.application.monitor.Logger;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.randomlogic.rlpay.model.domain.payment.User;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import com.randomlogic.rlpay.model.domain.interfaces.ILogs;
import com.randomlogic.rlpay.model.service.impl.LogsAccessSvcImpl;
import com.randomlogic.rlpay.model.service.interfaces.ILogsAccessSvc;
import org.apache.commons.validator.routines.DateValidator;
import org.springframework.stereotype.Controller;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;
import com.randomlogic.rlpay.application.util.ServletParams;
import static com.randomlogic.rlpay.portal.payment.domain.PortalErrorCodes.*;

/**
 *
 * @author Paul.Allen
 */
@Controller
public class LogsController implements Serializable
{
    private ServletParams params;
    private String resultPage = "/admin/logs.xhtml";
    private User user = null;

    private HttpSession session;

    private String transId = "0";

    private String accountNo = "0";

    private Date beginDate = (new Date());

    private Date endDate = (new Date());

    private String errorCode = "0";

    private ConfigBean cfgBean = null;

    public void setTransId (String transId)
    {
        this.transId = transId;
    }

    public String getTransId()
    {
        return this.transId;
    }

    public void setSession()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        session = (HttpSession) facesContext.getExternalContext().getSession (false);
        Object request;
        Object response;

        if (session == null)
        {
            user = new User();
            session.setAttribute ("userBean", user);
            session.setAttribute ("expired", true);
            resultPage = "/login/logResult.xhtml";
        }
        else
        {
            user = (User)session.getAttribute ("userBean");
        }

        ServletContext temp = (ServletContext)facesContext.getExternalContext().getContext();
        request = facesContext.getExternalContext().getRequest();
        response = facesContext.getExternalContext().getResponse();

        params = new ServletParams (new ErrorBean(), temp, (HttpServletRequest)request, (HttpServletResponse)response);
        cfgBean = (ConfigBean)params.getContext().getAttribute ("cfgBean");
    }

    public HttpSession getSession()
    {
        return this.session;
    }

    public Collection<Logs> search()
    {
        DateValidator dateValidator = DateValidator.getInstance();
        Date begin;
        Date end;
        ILogsAccessSvc logAccess = new LogsAccessSvcImpl();
        ILogs searchLog;
        Collection<Logs> logList;
        LogData logData = new LogData();

        // For input type datetime-local which has a "T" between date and time
////        if (getBeginDate() != null)
////        {
////            setBeginDate (getBeginDate().replace ("T", " "));
////        }
        // else do nothing

////        if (getEndDate() != null)
////        {
////            setEndDate (getEndDate().replace ("T", " "));
////        }
        // else do nothing

        begin = dateValidator.validate (getBeginDate().toString(), "yyyy-MM-dd HH:mm");

        dateValidator = DateValidator.getInstance();
        end = dateValidator.validate (getEndDate().toString(), "yyyy-MM-dd HH:mm");

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
        logData.setClientip ("0");
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
////        params.getRequest().getSession().setAttribute ("logList", logList);

        Logger.log (LogsController.class, "LogsController Search\n\n" + params.getUri());

////        try
////        {
////            params.getContext().getRequestDispatcher (resultPage).forward (params.getRequest(), params.getResponse());
////        }
////        catch (ServletException | IOException ex)
////        {
////            Logger.log (LogsController.class, ex, "LogsController: Exception.\n");
////
////            if (params.getError() != null)
////            {
////                params.getRequest().getSession().removeAttribute ("errorMsg");
////                params.getRequest().getSession().setAttribute ("errorMsg", params.getError());
////                try
////                {
////                    params.getError().generateError (params.getContext(),
////                            params.getRequest(),
////                            params.getResponse(),
////                            "Request",
////                            params.getUri(),
////                            ErrorBean.ERROR_INVALID_URI,
////                            "Requested page not found",
////                            "/genError.jsp",
////                            ex.getMessage(),
////                            ex, null
////                    );
////                }
////                catch (ServletException | IOException ex1)
////                {
////                    Logger.log (LogsController.class, ex, "LogsController: Exception.\n");
////                }
////            }
////        }

        return logList;
    }

    /**
     * @return the accountNo
     */
    public String getAccountNo()
    {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo (String accountNo)
    {
        this.accountNo = accountNo;
    }

    /**
     * @return the beginDate
     */
    public Date getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate (Date beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate (Date endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }
}
