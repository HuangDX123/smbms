package com.huang.filter;

import com.huang.pojo.User;
import com.huang.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

//        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//
//
//        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
//
//        if (user == null ) {
//            response.sendRedirect("/index/error.jsp");
//        } else {
//            chain.doFilter(req, resp);
//        }
//
//
//    }
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    //        过滤器 从Session中获取
    User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);

    System.out.println(user);

    if (user == null) { //用户未登录
        resp.sendRedirect(req.getContextPath() + "/error.jsp");
        System.out.println("用户未登录");
    } else { //用户以登陆
        System.out.println("用户已登录");
        chain.doFilter(req, resp);
    }
    System.out.println("SystemFilter已过滤");
}


    public void destroy() {

    }
}
