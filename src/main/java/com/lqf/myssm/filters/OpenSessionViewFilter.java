package com.lqf.myssm.filters;

import com.lqf.myssm.transaction.TransactionManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("*.do")
public class OpenSessionViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //开启事务
            TransactionManager.beginTrans();
            filterChain.doFilter(servletRequest,servletResponse);
            //提交事务
            TransactionManager.commit();
        }catch (Exception e){
            System.out.println("发生异常，回滚了");
            TransactionManager.rollback();
        }

    }
}
