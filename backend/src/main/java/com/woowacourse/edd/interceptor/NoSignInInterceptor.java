package com.woowacourse.edd.interceptor;

import com.woowacourse.edd.exceptions.InvalidAccessException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class NoSignInInterceptor extends HandlerInterceptorAdapter {

    private List<String> allowedMethodsToVideos = Arrays.asList("GET");
    private List<String> allowedMethodsToUsers = Arrays.asList("GET", "POST");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isSignedIn(request) && isLogoutURI(request)) {
            return true;
        }

        if (isSignedIn(request) &&
            isVideosURI(request) &&
            allowedMethodsToVideos.contains(request.getMethod())) {
            return true;
        }

        if (isSignedIn(request) &&
            isUsersURI(request) &&
            allowedMethodsToUsers.contains(request.getMethod())) {
            return true;
        }

        throw new InvalidAccessException();
    }

    private boolean isLogoutURI(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/logout");
    }

    private boolean isSignedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private boolean isUsersURI(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/users");
    }

    private boolean isVideosURI(HttpServletRequest request) {
        return request.getRequestURI().contains("/v1/videos");
    }
}
