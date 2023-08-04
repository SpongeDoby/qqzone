package com.lqf.service.impl;

import com.lqf.dao.UserBasicDao;
import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;
import com.lqf.service.TopicService;
import com.lqf.service.UserBasicService;

import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDao userBasicDao;
    private TopicService topicService;

    @Override
    public UserBasic login(String loginId, String password) {
        try {
            UserBasic userBasic = userBasicDao.getUserBasic(loginId, password);
            if(userBasic!=null){
                List<UserBasic> friendList = getFriendList(userBasic);
                List<Topic> topicList = topicService.getTopicList(userBasic);
                userBasic.setFriendList(friendList);
                userBasic.setTopicList(topicList);
            }
            return userBasic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        List<Integer> friendIdList = userBasicDao.getFriendIdList(userBasic);
        List<UserBasic> friendList=new ArrayList<>(friendIdList.size());
        try {
            for(Integer fid:friendIdList){
                friendList.add(userBasicDao.getUserBasicById(fid));
            }
            return friendList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        UserBasic userBasicById = userBasicDao.getUserBasicById(id);
        List<Topic> topicList = topicService.getTopicList(userBasicById);
        userBasicById.setTopicList(topicList);
        return userBasicById;
    }
}
