/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.view.controller.bean;

import com.randomlogic.rlpay.view.controller.impl.LogsController;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.randomlogic.rlpay.model.domain.company.entity.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
@ManagedBean
@SessionScoped
public class LogsBean implements Serializable
{
    @Autowired
    private LogsController controller;

    private Collection<Logs> logList;

    public void setLogsController (LogsController controller)
    {
        this.controller = controller;
    }

    public void search()
    {
        controller.setSession();
        logList = controller.search();
    }

    public void setTransId (String transId)
    {
        controller.setTransId (transId);
    }

    public String getTransId()
    {
        return controller.getTransId();
    }

        /**
     * @return the accountNo
     */
    public String getAccountNo()
    {
        return controller.getAccountNo();
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo (String accountNo)
    {
        controller.setAccountNo (accountNo);
    }

    /**
     * @return the beginParm
     */
    public Date getBeginDate()
    {
        return controller.getBeginDate();
    }

    /**
     * @param beginParm the beginParm to set
     */
    public void setBeginDate (Date beginDate)
    {
        controller.setBeginDate (beginDate);
    }

    /**
     * @return the endParm
     */
    public Date getEndDate()
    {
        return controller.getEndDate();
    }

    /**
     * @param endParm the endParm to set
     */
    public void setEndDate (Date endDate)
    {
        controller.setEndDate (endDate);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode()
    {
        return controller.getErrorCode();
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode (String errorCode)
    {
        controller.setErrorCode (errorCode);
    }

    public Collection<Logs> getLogList()
    {
        return this.logList;
    }

    public void setLogList (Collection<Logs> logList)
    {
        this.logList = logList;
    }
}
