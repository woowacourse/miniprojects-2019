package com.woowacourse.sunbook.seongmo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login interceptor");
        HttpSession session = request.getSession();
        Object loginSession = session.getAttribute("loginUser");

        if (loginSession == null) {
            log.info("fail to access request location by interceptor");
            response.sendRedirect("/");
            return false;
        }
        log.info("success to access request location");
        return true;
    }

}
