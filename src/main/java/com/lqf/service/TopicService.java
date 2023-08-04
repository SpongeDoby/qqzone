package com.lqf.service;

import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;

import java.util.List;

public interface TopicService {
    List<Topic> getTopicList(UserBasic userBasic);

    void delTopic(Integer id);

    void addTopic(Topic topic);

    Topic getTopic(Integer id);
}
