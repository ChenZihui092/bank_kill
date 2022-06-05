package com.example.bank_kill.config;

import com.alibaba.fastjson.JSON;
import com.example.bank_kill.util.SessionUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (SessionUtil.getUserFromSession(request.getSession()) == null) {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/json;charset=UTF-8");
            HashMap<String, Object> ma = new HashMap<>();
            ma.put("code", response.SC_UNAUTHORIZED);
            ma.put("msg", "请先登录");
            PrintWriter writer = response.getWriter();
            writer.println(JSON.toJSONString(ma));//转换成JSON格式返回
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
