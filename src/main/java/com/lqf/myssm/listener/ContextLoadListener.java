package com.lqf.myssm.listener;

import com.lqf.myssm.ioc.BeanFactory;
import com.lqf.myssm.ioc.ClassPathXmlApplicationContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        String path = application.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory=new ClassPathXmlApplicationContext(path);
        application.setAttribute("beanFactory",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
