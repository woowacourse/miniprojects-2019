package com.woowacourse.edd.interceptor;

import com.woowacourse.edd.exceptions.UnauthenticatedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class NoSignInRequiredInterceptor extends HandlerInterceptorAdapter {

    private final List<String> allowedMethods;

    public NoSignInRequiredInterceptor(String... allowedMethods) {
        this.allowedMethods = Arrays.asList(allowedMethods);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();

        if (isSignedIn(request.getSession()) ||
            shouldNotVerify(method)) {
            return true;
        }

        throw new UnauthenticatedException();
    }

    private boolean shouldNotVerify(String method) {
        return !allowedMethods.contains(method);
    }

    private boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}
