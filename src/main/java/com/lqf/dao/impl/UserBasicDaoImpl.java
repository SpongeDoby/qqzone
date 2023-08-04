package com.lqf.dao.impl;

import com.lqf.myssm.utils.JdbcUtils;
import com.lqf.dao.UserBasicDao;
import com.lqf.entity.UserBasic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBasicDaoImpl implements UserBasicDao {
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    @Override
    public UserBasic getUserBasic(String loginId, String password) {
        String sql="select * from t_user_basic where loginId=? and pwd=?";
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,loginId);
            preparedStatement.setString(2,password);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String loginId1 = resultSet.getString("loginId");
                String nikeName = resultSet.getString("nickName");
                String pwd = resultSet.getString("pwd");
                String headImg = resultSet.getString("headImg");
                JdbcUtils.release(preparedStatement,resultSet);
                return new UserBasic(id,loginId1,nikeName,pwd,headImg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Integer> getFriendIdList(UserBasic userBasic) {
        //按照编码规范，不用联表查询(其实是不准多张表联表），先返回好友ID列表，在业务层组合多个细粒度操作完成业务逻辑，这里直接用下面注释中的联表会快一点
        String sql="select * from t_friend where uid=?";
        List<Integer> friendIdList=new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userBasic.getId());
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                friendIdList.add(resultSet.getInt("fid"));
            }
            JdbcUtils.release(preparedStatement,resultSet);
            return friendIdList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        一步到位的连表查询的写法
//        String sql="SELECT t3.* FROM t_user_basic t1 LEFT JOIN t_friend t2 ON t1.id=t2.uid INNER JOIN t_user_basic t3 ON t2.fid=t3.id WHERE t1.id=?";
//        List<UserBasic> friendList=new ArrayList<>();
//        try {
//            Connection connection = JdbcUtils.getConnection();
//            preparedStatement=connection.prepareStatement(sql);
//            preparedStatement.setInt(1,userBasic.getId());
//            resultSet = preparedStatement.executeQuery();
//            while(resultSet.next()){
//                int id = resultSet.getInt(1);
//                String loginId1 = resultSet.getString(2);
//                String nikeName = resultSet.getString(3);
//                String pwd = resultSet.getString(4);
//                String headImg = resultSet.getString(5);
//                friendList.add(new UserBasic(id,loginId1,nikeName,pwd,headImg));
//            }
//            return friendList;
//        }catch (SQLException e){
//            e.printStackTrace();
//            throw new RuntimeException();
//        }

//  子查询写法
//        String sql="select * from t_friend where uid=?";
//        List<Integer> friendIdList=new ArrayList<>();
//        List<UserBasic> friendList=new ArrayList<>();
//        try {
//            Connection connection = JdbcUtils.getConnection();
//            preparedStatement=connection.prepareStatement(sql);
//            preparedStatement.setInt(1,userBasic.getId());
//            resultSet= preparedStatement.executeQuery();
//            while(resultSet.next()){
//                friendIdList.add(resultSet.getInt("fid"));
//            }
//            JdbcUtils.release(preparedStatement,resultSet);
//
//            //根据好友ID列表拿到对应的用户列表
//            for (Integer id : friendIdList) {
//                sql="seelct * from  t_user_basic where id=?";
//                preparedStatement=connection.prepareStatement(sql);
//                preparedStatement.setInt(1,id);
//                resultSet= preparedStatement.executeQuery();
//                while(resultSet.next()){
//                    int id1 = resultSet.getInt("id");
//                    String loginId = resultSet.getString("loginId");
//                    String nikeName = resultSet.getString("nikeName");
//                    String pwd = resultSet.getString("pwd");
//                    String headImg = resultSet.getString("headImg");
//                    friendList.add(new UserBasic(id1,loginId,nikeName,pwd,headImg));
//                }
//            }
//            JdbcUtils.release(preparedStatement,resultSet);
//            return friendList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public UserBasic getUserBasicById(Integer fid) {
        String sql="select * from t_user_basic where id=?";
        try {
            Connection connection = JdbcUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,fid);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String loginId1 = resultSet.getString("loginId");
                String nikeName = resultSet.getString("nickName");
                String pwd = resultSet.getString("pwd");
                String headImg = resultSet.getString("headImg");
                JdbcUtils.release(preparedStatement,resultSet);
                return new UserBasic(id,loginId1,nikeName,pwd,headImg);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
