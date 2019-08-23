package com.woowacourse.edd.application.service;

import com.woowacourse.edd.application.dto.LoginRequestDto;
import com.woowacourse.edd.domain.User;
import com.woowacourse.edd.exceptions.PasswordNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LoginInternalService {

    private final UserInternalService userInternalService;

    @Autowired
    public LoginInternalService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public User authenticate(LoginRequestDto loginRequestDto) {
        User user = userInternalService.findByEmail(loginRequestDto.getEmail());
        if (user.isNotMatchPassword(loginRequestDto.getPassword())) {
            throw new PasswordNotMatchException();
        }
        return user;
    }
}
