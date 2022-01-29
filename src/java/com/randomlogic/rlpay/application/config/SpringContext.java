/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul.Allen
 */
@Component
public class SpringContext implements ApplicationContextAware
{
    private static ApplicationContext context;

    /**
     * Returns the Spring managed bean instance of the given class type if it exists.
     * Returns null otherwise.
     * @param <T>
     * @param beanClass
     * @return
     */
    public static <T extends Object> T getBean(Class<T> beanClass)
    {
        return context.getBean (beanClass);
    }

    @Override
    public void setApplicationContext (ApplicationContext context) throws BeansException
    {
        // store ApplicationContext reference to access required beans later on
        SpringContext.context = context;
    }
}
