/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.listeners;

import com.randomlogic.rlpay.application.config.Config;
import com.randomlogic.rlpay.application.monitor.ErrorBean;
import com.randomlogic.rlpay.application.monitor.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web session lifecycle listener.
 *
 * @author Paul.Allen
 */
public class SessionListener implements ServletContextListener,
                                        HttpSessionAttributeListener,
                                        HttpSessionListener
{
    private static final int DEBUG = Logger.DEBUG;

    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;
    private Config cfgBean = null;

/**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The session attribute event
     */
    @Override
    public void attributeAdded (HttpSessionBindingEvent event)
    {
        log ("attributeAdded('" + event.getSession().getId() + "', '" +
             event.getName() + "', '" + event.getValue() + "')");
    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The session attribute event
     */
    @Override
    public void attributeRemoved (HttpSessionBindingEvent event)
    {
        log ("attributeRemoved('" + event.getSession().getId() + "', '" +
             event.getName() + "', '" + event.getValue() + "')");
    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The session attribute event
     */
    @Override
    public void attributeReplaced (HttpSessionBindingEvent event)
    {
        log ("attributeReplaced('" + event.getSession().getId() + "', '" +
             event.getName() + "', '" + event.getValue() + "')");
    }


    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextDestroyed (ServletContextEvent event)
    {
        log ("contextDestroyed()");
        this.context = null;
    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextInitialized (ServletContextEvent event)
    {
        this.context = event.getServletContext();
        log ("contextInitialized()");
    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    @Override
    public void sessionCreated (HttpSessionEvent event)
    {
        boolean result = true;

        Logger.debug (SessionListener.class, DEBUG, "sessionCreated()");

        cfgBean = (Config)context.getAttribute ("cfgBean");

        if (cfgBean != null)
        {
            if (cfgBean.checkForUpdates())
            {
                context.setAttribute ("cfgBean", cfgBean);
            }
            else
            {
                log ("SEVERE: Failed to read application configs from database!");
            }
        }
        else
        {
           log ("SEVERE: Failed to read application configs from context!");
        }

        try
        {
            Context initialContext = new InitialContext();
            Context env = (Context) initialContext.lookup ("java:comp/env");

            if (env == null)
            {
                log ("Could not create env");
                result = false;
            }

            if (result)
            {
                if (result)
                {
                    ErrorBean errorMsg = new ErrorBean();
                    event.getSession().setAttribute ("errorMsg", errorMsg);
                    log ("sessionCreated ('" + event.getSession().getId() + "')");
                }
                else
                {
                    log ("sessionCreated() FAILED!");
                }
            }
        }
        catch (NamingException ex)
        {
            log (ex.getMessage(), ex);
        }
    }

    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    @Override
    public void sessionDestroyed (HttpSessionEvent event)
    {
        event.getSession().removeAttribute ("errorMsg");
        event.getSession().removeAttribute ("userBean");
        event.getSession().removeAttribute ("clientBean");

        log ("sessionDestroyed ('" + event.getSession().getId() + "')");
    }


    // -------------------------------------------------------- Private Methods


    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
    private void log (String message)
    {
        if (context != null)
        {
            context.log ("SessionListener: " + message);
        }
        else
        {
            Logger.log (SessionListener.class, message);
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
            context.log ("SessionListener: " + message, exception);
        }
        else
        {
            Logger.log (Logger.EXCEPTION, SessionListener.class, exception, message);
        }
    }
}
