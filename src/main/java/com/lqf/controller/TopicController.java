package com.lqf.controller;


import com.lqf.entity.Topic;


import com.lqf.entity.UserBasic;
import com.lqf.service.TopicService;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;


public class TopicController {
    private TopicService topicService;


    public String topicDetail(Integer id, HttpSession session){
        Topic topic = topicService.getTopic(id);
        session.setAttribute("topic",topic);
        return "frames/detail";
    }

    public String delTopic(Integer topicId){
        topicService.delTopic(topicId);
        //更显操作完成后因更新session.userBasic.topicList，否则看不到更新后的结果
        return "redirect:topic.do?operateType=refreshList";
    }

    public String addTopic(String title,String content,HttpSession session){
        UserBasic userBasic =(UserBasic) session.getAttribute("userBasic");
        Topic topic = new Topic(title, content, LocalDateTime.now(), userBasic);
        topicService.addTopic(topic);
        //更显操作完成后因更新session.userBasic.topicList，否则看不到更新后的结果
        return "redirect:topic.do?operateType=refreshList";
    }

    public String refreshList(HttpSession session){
        UserBasic userBasic =(UserBasic) session.getAttribute("userBasic");
        List<Topic> topicList = topicService.getTopicList(userBasic);
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);
        return "redirect:page.do?operateType=page&page=frames/main";
    }
}
