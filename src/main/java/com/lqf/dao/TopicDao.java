package com.lqf.dao;

import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;

import java.util.List;

public interface TopicDao {
    //获取指定用户的日志列表
    List<Topic> getTopicList(UserBasic userBasic);
    //添加日志
    void addTopic(Topic topic);
    //删除日志
    void deleteTopic(Integer id);
    //获取指定日志信息
    Topic getTopic(Integer id);

}
