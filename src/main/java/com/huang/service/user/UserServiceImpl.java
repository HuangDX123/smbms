package com.huang.service.user;

import com.huang.dao.BaseDao;
import com.huang.dao.user.UserDao;
import com.huang.dao.user.UserDaoImpl;
import com.huang.pojo.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    //    业务层都会调用Dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    public User login(String userCode, String password) {
        Connection connection;
        connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            //    通过业务层调用对应的具体的数据库操作
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }


    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "123456");
        System.out.println(admin.getUserPassword());
    }

}
