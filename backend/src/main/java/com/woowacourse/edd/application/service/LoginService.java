package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.UserConverter;
import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.response.LoginUserResponse;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginInternalService loginInternalService;
    private final UserInternalService userInternalService;

    @Autowired
    public LoginService(LoginInternalService loginInternalService, UserInternalService userInternalService) {
        this.loginInternalService = loginInternalService;
        this.userInternalService = userInternalService;
    }

    public SessionUser login(LoginRequestDto loginRequestDto) {
        User user = loginInternalService.authenticate(loginRequestDto);
        return UserConverter.toSessionUser(user);
    }

    public LoginUserResponse lookUp(Long sessionId) {
        User user = userInternalService.findById(sessionId);
        return UserConverter.toLoginUserResponse(user);
    }
}
