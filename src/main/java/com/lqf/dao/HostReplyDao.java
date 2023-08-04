package com.lqf.dao;

import com.lqf.entity.HostReply;

import java.util.List;

public interface HostReplyDao {
    HostReply getHostReply(Integer rid);

    void delHostReplyByRid(Integer rid);

    void delHostReplyById(Integer id);

    void addHostReply(HostReply hostReply);
}
