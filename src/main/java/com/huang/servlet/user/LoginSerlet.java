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

        System.out.println("前端:" + userCode + ":" + userPassword);


//        和数据库中的密码进行对比,调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);

        System.out.println(user);
        System.out.println("数据库:" + user.getUserCode() + ":" + user.getUserPassword());

//        if (user != null) {
//            req.getSession().setAttribute(Constants.USER_SESSION, user);
////            跳转到主页
//            resp.sendRedirect("jsp/frame.jsp");
//        } else {
//            req.setAttribute("error", "用户名或者密码不正确");
//            req.getRequestDispatcher("login.jsp").forward(req, resp);
//        }

        //判断用户
        if (user.getId() != null) {
            if (userPassword.equals(user.getUserPassword())) {
                //将用户信息添加到Session中
                req.getSession().setAttribute(Constants.USER_SESSION, user);
                resp.sendRedirect("jsp/frame.jsp");
                System.out.println("登陆成功");
            } else {
                req.setAttribute("error", "密码错误");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                System.out.println("密码错误");
            }
        }
        if (userCode.equals(user.getUserName())) {

            req.setAttribute("error", "用户不存在");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            System.out.println("用户不存在");

        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
