package com.huang.service.user;

import com.huang.pojo.User;

public interface UserService {
    //    用户登录
    public User login(String userCode, String password);

    //    修改当前用户密码
    public boolean updatePwd(int id, String pwd);
}
