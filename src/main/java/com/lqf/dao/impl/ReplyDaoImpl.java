package com.lqf.dao.impl;

import com.lqf.myssm.utils.JdbcUtils;
import com.lqf.dao.ReplyDao;
import com.lqf.entity.Reply;
import com.lqf.entity.Topic;
import com.lqf.entity.UserBasic;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReplyDaoImpl implements ReplyDao {
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;

    @Override
    public List<Reply> getReplyList(Integer topicId) {
        String sql="select * from t_reply where topic=?";
        List<Reply> replyList=new ArrayList<>();
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,topicId);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
//                Timestamp replyDateTimStamp = resultSet.getTimestamp("replyDate");
//                LocalDateTime replyDate=replyDateTimStamp.toLocalDateTime();
                LocalDateTime replyDate=(LocalDateTime) resultSet.getObject("replyDate");
                int author = resultSet.getInt("author");
                int topic = resultSet.getInt("topic");
                Reply reply=new Reply(id,content,replyDate,new UserBasic(author),new Topic(topic));
                replyList.add(reply);
            }
            JdbcUtils.release(preparedStatement,resultSet);
            return replyList;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addReply(Reply reply) {
        String sql="insert into t_reply (content,replyDate,author,topic) values(?,?,?,?)";
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,reply.getContent());
            preparedStatement.setObject(2,reply.getReplyDate());
            preparedStatement.setInt(3,reply.getAuthor().getId());
            preparedStatement.setInt(4,reply.getTopic().getId());
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,resultSet);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteReply(Integer id) {
        String sql="delete from t_reply where id=?";
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,resultSet);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
