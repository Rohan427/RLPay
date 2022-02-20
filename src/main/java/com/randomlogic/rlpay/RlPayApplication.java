package com.randomlogic.rlpay;

import com.randomlogic.rlpay.application.config.Config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@ComponentScan ("com.randomlogic.rlpay")
public class RlPayApplication extends SpringBootServletInitializer implements WebApplicationInitializer
{
    @Autowired (required = true) Config cfgBean;

	public static void main (String[] args)
    {
		SpringApplication.run (RlPayApplication.class, args);
	}

    @Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application)
    {
		return application.sources (RlPayApplication.class);
	}

    @Bean
    public ServletContextInitializer initializer()
    {
        return new ServletContextInitializer()
        {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException
            {
                cfgBean.checkForUpdates();

                servletContext.setAttribute ("cfgBean", cfgBean);
            }
        };
}
}
