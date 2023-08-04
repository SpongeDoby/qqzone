package com.lqf.service.impl;

import com.lqf.dao.HostReplyDao;
import com.lqf.dao.ReplyDao;
import com.lqf.dao.UserBasicDao;
import com.lqf.entity.HostReply;
import com.lqf.entity.Reply;
import com.lqf.entity.UserBasic;
import com.lqf.service.HostReplyService;
import com.lqf.service.ReplyService;
import com.lqf.service.UserBasicService;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    private ReplyDao replyDao;
    private UserBasicService userBasicService;
    private HostReplyService hostReplyService;

    @Override
    public List<Reply> getReplyList(Integer topicId) {
        List<Reply> replyList = replyDao.getReplyList(topicId);
        for(Reply reply:replyList){
            HostReply hostReply=hostReplyService.getHostReply(reply.getId());
            reply.setHostReply(hostReply);
            UserBasic userBasicById = userBasicService.getUserBasicById(reply.getAuthor().getId());
            reply.setAuthor(userBasicById);
        }
        return  replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDao.addReply(reply);
    }

    @Override
    public void deleteReply(Integer id) {
        hostReplyService.delByRid(id);
        replyDao.deleteReply(id);
    }
}
