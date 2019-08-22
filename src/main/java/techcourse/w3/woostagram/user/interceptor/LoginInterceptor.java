package techcourse.w3.woostagram.user.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static techcourse.w3.woostagram.common.support.UserEmailArgumentResolver.LOGGED_IN_USER_SESSION_KEY;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> userSession = Optional.ofNullable((String) request.getSession().getAttribute(LOGGED_IN_USER_SESSION_KEY));
        log.debug("PATH : {}", request.getRequestURI());
        log.debug("METHOD : {}", request.getMethod());
        log.debug("LOGIN : {}", userSession.isPresent());

        if (userSession.isPresent()) {
            return true;
        }
        response.sendRedirect("/users/login/form");
        return false;
    }
}
