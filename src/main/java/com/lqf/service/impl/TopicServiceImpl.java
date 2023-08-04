package com.lqf.service.impl;

import com.lqf.dao.HostReplyDao;
import com.lqf.dao.TopicDao;
import com.lqf.dao.UserBasicDao;
import com.lqf.entity.Reply;
import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;
import com.lqf.service.ReplyService;
import com.lqf.service.TopicService;
import com.lqf.service.UserBasicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao;
    private UserBasicService userBasicService;
    private ReplyService replyService;

    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        List<Topic> topicList = topicDao.getTopicList(userBasic);
        return topicList;
    }

    @Override
    public void delTopic(Integer id) {
        Topic topic = getTopic(id);
        List<Reply> replies = topic.getReplies();
        for(Reply reply:replies){
            replyService.deleteReply(reply.getId());
        }
        topicDao.deleteTopic(id);
    }

    @Override
    public Topic getTopic(Integer id) {
        Topic topic = topicDao.getTopic(id);
        UserBasic userBasicById = userBasicService.getUserBasicById(topic.getAuthor().getId());
        topic.setAuthor(userBasicById);
        List<Reply> replyList = replyService.getReplyList(id);
        topic.setReplies(replyList);
        return topic;
    }

    @Override
    public void addTopic(Topic topic) {
        topicDao.addTopic(topic);
    }
}
