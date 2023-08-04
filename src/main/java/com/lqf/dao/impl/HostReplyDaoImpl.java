package com.lqf.dao.impl;

import com.lqf.dao.HostReplyDao;
import com.lqf.myssm.utils.JdbcUtils;
import com.lqf.entity.HostReply;
import com.lqf.entity.Reply;
import com.lqf.entity.UserBasic;

import java.sql.*;
import java.time.LocalDateTime;

public class HostReplyDaoImpl implements HostReplyDao {
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;

    @Override
    public HostReply getHostReply(Integer rid) {
        String sql="select * from t_host_reply where reply=?";
        HostReply hostReply=null;
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,rid);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                Timestamp hostReplyDateTimeStamp = resultSet.getTimestamp("hostReplyDate");
                LocalDateTime hostReplyDate=hostReplyDateTimeStamp.toLocalDateTime();
                int author = resultSet.getInt("author");
                int reply = resultSet.getInt("reply");
                hostReply = new HostReply(id, content, hostReplyDate, new UserBasic(author), new Reply(reply));
            }
            JdbcUtils.release(preparedStatement,resultSet);
            return  hostReply;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delHostReplyByRid(Integer rid) {
        String sql="delete from t_host_reply where reply=?";
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,rid);
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,resultSet);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delHostReplyById(Integer id) {
        String sql="delete from t_host_reply where id=?";
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

    @Override
    public void addHostReply(HostReply hostReply) {
        String sql="insert into t_host_reply (content,hostReplyDate,author,reply) values (?,?,?,?)";
        try{
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,hostReply.getContent());
            preparedStatement.setObject(2,hostReply.getHostReplyDate());
            preparedStatement.setInt(3,hostReply.getAuthor().getId());
            preparedStatement.setInt(4,hostReply.getReply().getId());
            preparedStatement.executeUpdate();
            JdbcUtils.release(preparedStatement,resultSet);
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
