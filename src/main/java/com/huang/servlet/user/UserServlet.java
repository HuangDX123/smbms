package com.huang.servlet.user;

import com.huang.pojo.User;
import com.huang.service.user.UserService;
import com.huang.service.user.UserServiceImpl;
import com.huang.util.Constants;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我运行了1");
//       从Session你们拿id;
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        boolean flag = false;


        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
            System.out.println("我运行了2");


            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);

            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，重新登录");
//                密码修改成功移除当前Session
                System.out.println("到了这里");
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "修改密码失败");

            }
        } else {
            req.setAttribute("message", "新密码有误");

        }
        System.out.println("我运行了3");

        req.getRequestDispatcher("error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
