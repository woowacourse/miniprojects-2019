package com.wootecobook.turkey.user.service;

import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findById(Long id) {
        return new User();
    }
}
