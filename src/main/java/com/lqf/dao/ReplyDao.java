package com.lqf.dao;

import com.lqf.entity.Reply;
import com.lqf.entity.Topic;

import java.util.List;

public interface ReplyDao {
    //获得指定日志的回复列表
    List<Reply> getReplyList(Integer topicId);
    //添加回复
    void addReply(Reply reply);
    //删除回复
    void deleteReply(Integer id);
}
