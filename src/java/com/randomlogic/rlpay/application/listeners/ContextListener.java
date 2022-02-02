/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.listeners;

import com.randomlogic.rlpay.application.config.Config;
import com.randomlogic.rlpay.application.monitor.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.randomlogic.rlpay.portal.payment.domain.ConfigBean;

/**
 * Web application lifecycle listener.
 *
 * @author Paul.Allen
 */
public class ContextListener implements ServletContextAttributeListener, ServletContextListener
{
    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;
    private Config cfg = null;
    private Config cfgBean = null;

    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeAdded (ServletContextAttributeEvent event)
    {
        log ("attributeAdded ('" + event.getName() + "', '" +
             event.getValue() + "')");
    }

    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeRemoved (ServletContextAttributeEvent event)
    {
        log ("attributeRemoved ('" + event.getName() + "', '" +
             event.getValue() + "')");
    }

    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeReplaced (ServletContextAttributeEvent event)
    {
        log ("attributeReplaced ('" + event.getName() + "', '" +
             event.getValue() + "')");
    }

    @Override
    public void contextInitialized (ServletContextEvent event)
    {
        context = event.getServletContext();
        cfgBean = cfg = new Config();
        context.setAttribute ("cfg", cfg);

        context.setAttribute ("cfgBean", cfgBean);
        log ("contextInitialized()");
    }

    @Override
    public void contextDestroyed (ServletContextEvent sce)
    {
    }

    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
    private void log (String message)
    {
        if (context != null)
        {
            context.log ("ContextListener: " + message);
        }
        else
        {
            Logger.log (ContextListener.class, message);
        }

    }

    /**
     * Log a message and associated exception to the servlet context
     * application log.
     *
     * @param message Message to be logged
     * @param exception Exception to be logged
     */
    private void log (String message, Exception exception)
    {
        if (context != null)
        {
            context.log ("ContextListener: " + message, exception);
        }
        else
        {
            Logger.log (Logger.EXCEPTION, ContextListener.class, exception, message);
        }
    }
}
