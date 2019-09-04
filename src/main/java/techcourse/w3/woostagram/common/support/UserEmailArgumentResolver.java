package techcourse.w3.woostagram.common.support;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserEmailArgumentResolver implements HandlerMethodArgumentResolver {
    public static final String LOGGED_IN_USER_SESSION_KEY = "loggedInUser";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoggedInUser.class);
    }

    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Optional<UserInfoDto> userSession = Optional.ofNullable((UserInfoDto) request.getSession().getAttribute(LOGGED_IN_USER_SESSION_KEY));
        return userSession.get().getEmail();
    }
}
