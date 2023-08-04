package com.lqf.controller;

import com.lqf.entity.HostReply;
import com.lqf.entity.Reply;
import com.lqf.entity.UserBasic;
import com.lqf.service.HostReplyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

public class HostReplyController {
    private HostReplyService hostReplyService;

    public String addHostReply(String content,Integer reply_id,Integer reply_topic_id,HttpSession session){
        UserBasic userBasic =(UserBasic) session.getAttribute("userBasic");
        HostReply hostReply = new HostReply(content, LocalDateTime.now(), userBasic, new Reply(reply_id));
        hostReplyService.addHostReply(hostReply);
        return "redirect:topic.do?operateType=topicDetail&id="+reply_topic_id;
    }

    public String delHostReply(Integer hostReplyId,Integer topicId){
        hostReplyService.dekById(hostReplyId);
        return "redirect:topic.do?operateType=topicDetail&id="+topicId;
    }
}
