package com.lqf.service;

import com.lqf.dao.HostReplyDao;
import com.lqf.entity.HostReply;

public interface HostReplyService {
    HostReply getHostReply(Integer rid);

    void delByRid(Integer rid);

    void dekById(Integer id);

    void addHostReply(HostReply hostReply);
}
