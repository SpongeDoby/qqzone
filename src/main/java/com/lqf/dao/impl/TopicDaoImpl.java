package com.lqf.dao.impl;

import com.lqf.myssm.utils.JdbcUtils;
import com.lqf.dao.TopicDao;
import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl implements TopicDao {
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        String sql="select * from t_topic where author=?";
        List<Topic> topicList=new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userBasic.getId());
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp timestamp = resultSet.getTimestamp("topicDate");
                LocalDateTime topicDate = timestamp.toLocalDateTime();
                int author = resultSet.getInt("author");
                topicList.add(new Topic(id,title,content,topicDate,new UserBasic(author)));
            }
            JdbcUtils.release(preparedStatement,resultSet);
            return topicList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTopic(Topic topic) {
        String sql="insert into t_topic (title,content,topicDate,author) value (?,?,?,?)";
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,topic.getTitle());
            preparedStatement.setString(2,topic.getContent());
            preparedStatement.setObject(3,topic.getTopicDate());
            preparedStatement.setObject(4,topic.getAuthor().getId());
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,resultSet);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTopic(Integer id) {
        String sql="delete from t_topic where id=?";
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic getTopic(Integer id) {
        String sql="select * from t_topic where id=?";
        Topic topic=null;
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                Timestamp timestamp = resultSet.getTimestamp("topicDate");
                LocalDateTime topicDate = timestamp.toLocalDateTime();
                int author = resultSet.getInt("author");
                topic=new Topic(id1,title,content,topicDate,new UserBasic(author));
            }
            JdbcUtils.release(preparedStatement, resultSet);
            return topic;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
