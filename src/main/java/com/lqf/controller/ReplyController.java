package com.lqf.controller;

import com.lqf.entity.Reply;
import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;
import com.lqf.service.ReplyService;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class ReplyController {
    private ReplyService replyService;

    public String addReply(String content, Integer topicId,HttpSession session){
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        Topic topic=new Topic(topicId);
        LocalDateTime replyDate=LocalDateTime.now();
        Reply reply = new Reply(content, replyDate, userBasic, topic);
        replyService.addReply(reply);
        return "redirect:topic.do?operateType=topicDetail&id="+topicId;
    }

    public String delReply(Integer replyId,Integer topicId){
        replyService.deleteReply(replyId);
        return "redirect:topic.do?operateType=topicDetail&id="+topicId;
    }
}
