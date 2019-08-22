package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.converter.UserConverter;
import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final LoginInternalService loginInternalService;

    @Autowired
    public LoginService(LoginInternalService loginInternalService) {
        this.loginInternalService = loginInternalService;
    }

    public SessionUser login(LoginRequestDto loginRequestDto) {
        User user = loginInternalService.authenticate(loginRequestDto);
        return UserConverter.toSessionUser(user);
    }
}
