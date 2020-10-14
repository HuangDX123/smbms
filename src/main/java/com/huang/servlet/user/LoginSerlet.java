package com.huang.servlet.user;

import com.huang.pojo.User;
import com.huang.service.user.UserServiceImpl;
import com.huang.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSerlet extends HttpServlet {

//    Servlet:控制层调业务层

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--Start...");

//        获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

//        和数据库中的密码进行对比,调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        if (user != null) {
            req.getSession().setAttribute(Constants.USER_SESSION, user);
//            跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            req.setAttribute("error", "用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
