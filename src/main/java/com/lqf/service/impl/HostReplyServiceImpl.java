package com.lqf.service.impl;

import com.lqf.dao.HostReplyDao;
import com.lqf.entity.HostReply;
import com.lqf.service.HostReplyService;

public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDao hostReplyDao;

    @Override
    public HostReply getHostReply(Integer rid) {
        return hostReplyDao.getHostReply(rid);
    }

    @Override
    public void delByRid(Integer rid) {
        hostReplyDao.delHostReplyByRid(rid);
    }

    @Override
    public void dekById(Integer id) {
        hostReplyDao.delHostReplyById(id);
    }

    @Override
    public void addHostReply(HostReply hostReply) {
        hostReplyDao.addHostReply(hostReply);
    }
}
