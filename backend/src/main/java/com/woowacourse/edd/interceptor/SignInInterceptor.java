package com.woowacourse.edd.interceptor;

import com.woowacourse.edd.exceptions.InvalidAccessException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isNotSignedIn(request) ||
            isNotUserPost(request) ||
            isNotLoginPost(request)) {
            return true;
        }

        throw new InvalidAccessException();
    }

    private boolean isNotSignedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("user") == null;
    }

    private boolean isNotUserPost(HttpServletRequest request) {
        return isNotPost(request) && isUserURI(request);
    }

    private boolean isUserURI(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/users");
    }

    private boolean isNotLoginPost(HttpServletRequest request) {
        return isNotPost(request) && isLoginURI(request);
    }

    private boolean isLoginURI(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/login");
    }

    private boolean isNotPost(HttpServletRequest request) {
        return !"POST".equals(request.getMethod());
    }
}
