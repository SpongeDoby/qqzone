package com.lqf.myssm.mvc;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

public class ViewBaseServlet extends HttpServlet {

    TemplateEngine templateEngine;
    JakartaServletWebApplication jakartaServletWebApplication;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext=this.getServletContext();
        String viewPrefix=servletContext.getInitParameter("view-prefix");
        String viewSuffix=servletContext.getInitParameter("view-suffix");
        WebApplicationTemplateResolver resolver=new WebApplicationTemplateResolver(JakartaServletWebApplication.buildApplication(getServletContext()));
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix(viewPrefix);
        resolver.setSuffix(viewSuffix);
        resolver.setCacheTTLMs(60000L);
        resolver.setCacheable(true);
        resolver.setCharacterEncoding("UTF-8");
        templateEngine=new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }

    public void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(getServletContext());
        WebContext ctx = new WebContext(jakartaServletWebApplication.buildExchange(req, resp), req.getLocale(), jakartaServletWebApplication.getAttributeMap());
        templateEngine.process(templateName, ctx, resp.getWriter());
    }
}