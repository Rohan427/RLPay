/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.randomlogic.rlpay.application.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author Paul G. Allen <pgallen@gmail.com>
 */
@Component
public class ContextHolder implements ApplicationContextAware
{
    private static ApplicationContext context;

    @Override
    public void setApplicationContext( ApplicationContext applicationContext)
           throws BeansException
    {
        context = applicationContext;
    }

    public static ApplicationContext getContext()
    {
        return context;
    }
}
