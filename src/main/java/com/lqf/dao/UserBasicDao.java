package com.lqf.dao;

import com.lqf.entity.UserBasic;

import java.util.List;

public interface UserBasicDao {
    //根据用户名密码拿用户基础信息
    UserBasic getUserBasic(String username,String password);
    //获取指定用户的好友ID列表
    List<Integer> getFriendIdList(UserBasic userBasic);
    //根据ID获取用户
    UserBasic getUserBasicById(Integer fid);

}
