package com.lqf.service;

import com.lqf.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getReplyList(Integer topicId);
    void addReply(Reply reply);
    void deleteReply(Integer id);
}
