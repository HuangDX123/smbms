package com.huang.service.user;

import com.huang.pojo.User;

public interface UserService {
    //    用户登录
    public User login(String userCode, String password);
}
