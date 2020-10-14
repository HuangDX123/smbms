package com.huang.dao.user;

import com.huang.pojo.User;

import java.sql.Connection;

public interface UserDao {
//    得到要登录的用户
    public User getLoginUser(Connection connection,String userCode);

}
