package com.lqf.myssm.mvc;

import com.lqf.myssm.ioc.BeanFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;
    public DispatcherServlet(){}
    @Override
    public void init(){
        try {
            super.init();
            Object beanFactoryObj=this.getServletContext().getAttribute("beanFactory");
            if(beanFactoryObj!=null){
                beanFactory=(BeanFactory) beanFactoryObj;
            }else{
                throw new RuntimeException("IOC容器获取失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        //四个部分：路径解析、参数获取、方法调用、视图处理
        //路径解析
        String servletPath = req.getServletPath();
        servletPath=servletPath.substring(1,servletPath.lastIndexOf(".do"));
        Object controllerObj=beanFactory.getBean(servletPath);

        //方法调用
        String operateType = req.getParameter("operateType");
        if(operateType==null){
            operateType="index";
        }
        try {
            Method[] methods = controllerObj.getClass().getDeclaredMethods();
            for(Method method:methods){
                if(method.getName().equals(operateType)){
                    //参数获取
                    Parameter[] parameters = method.getParameters();
                    Object[] parameterObjects=new Object[parameters.length];
                    for(int i=0;i<parameters.length;i++){
                        //需要req做参数的方法
                        if(parameters[i].getName().equals("req")){
                            parameterObjects[i]=req;
                        } else if (parameters[i].getName().equals("resp")) {//需要resp做参数的方法
                            parameterObjects[i]=resp;
                        } else if (parameters[i].getName().equals("session")) {//需要session的方法
                            parameterObjects[i]=req.getSession();
                        } else{//普通参数
                            String parameter = req.getParameter(parameters[i].getName());
                            Object parameterObj=parameter;
                            if(parameterObj!=null){
                                //除了Integer，还要考虑Double、布尔等类型，SpringMVC中有一个更复杂的类型转换器来完成这个工作，而且可以自定义类型转换
                                if(parameters[i].getType().getName().equals("java.lang.Integer")){
                                    parameterObj=Integer.parseInt(parameter);
                                }
                            }
                            //没考虑单个参数多个值的情况，如接收对象、数组
                            parameterObjects[i]=parameterObj;
                        }
                    }
                    method.setAccessible(true);
                    Object methodReturnStr=method.invoke(controllerObj,parameterObjects);
                    //视图处理
                    String targetViewStr=(String)methodReturnStr;
                    if(targetViewStr.startsWith("redirect:")){
                        String redirectStr = targetViewStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else if(targetViewStr.startsWith("json:")){
                        String jsonStr=targetViewStr.substring("json:".length());
                        PrintWriter writer = resp.getWriter();
                        writer.println(jsonStr);
                        writer.flush();
                        writer.close();
                    }else{
                        super.processTemplate(targetViewStr,req,resp);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("异常来到了dispatcherServlet");
        }
    }
}
