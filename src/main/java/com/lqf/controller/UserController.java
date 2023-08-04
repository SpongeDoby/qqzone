package com.lqf.controller;

import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;
import com.lqf.service.TopicService;
import com.lqf.service.UserBasicService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UserController {
    private UserBasicService userBasicService;

    public String login(String loginId, String password, HttpSession session){
        UserBasic userBasic = userBasicService.login(loginId, password);
        if(userBasic!=null){
            //userBasic保存登陆者的信息，friend保存当前所在谁的空间的信息
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }
    }

    public String friend(Integer id,HttpSession session){
        UserBasic userBasicById = userBasicService.getUserBasicById(id);
        session.setAttribute("friend",userBasicById);
        return "index";
    }
}
