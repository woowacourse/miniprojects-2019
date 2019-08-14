package com.woowacourse.dsgram.web.argumentresolver;

import com.woowacourse.dsgram.service.dto.user.LoginUserDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserSessionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String USER = "sessionUser";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession httpSession = ((HttpServletRequest) webRequest.getNativeRequest()).getSession();
        return Optional.ofNullable((LoginUserDto) httpSession.getAttribute(USER))
                // TODO: 2019-08-14 Exception
                .orElseThrow(() -> new RuntimeException("로그인 후 이용할 수 있습니다."));
    }
}
