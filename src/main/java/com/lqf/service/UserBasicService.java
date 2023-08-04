package com.lqf.service;

import com.lqf.entity.UserBasic;

import java.util.List;

public interface UserBasicService {
    UserBasic login(String loginId,String password);
    List<UserBasic> getFriendList(UserBasic userBasic);

    UserBasic getUserBasicById(Integer id);
}
