package com.woowacourse.sunbook.domain.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private User user;
    private User updateUser;

    @BeforeEach
    void setUp() {
        user = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르", "lee"));
    }

    @Test
    void 이메일_변경() {
        updateUser = new User(new UserEmail("eara12sa@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르", "lee"));
        user.updateEmail(user, updateUser.getUserEmail());

        assertEquals(user.getUserEmail(), updateUser.getUserEmail());
    }

    @Test
    void 비밀번호_변경() {
        updateUser = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("1q2w3e4r!A"), new UserName("미르", "lee"));
        user.updatePassword(user, updateUser.getUserPassword());

        assertEquals(user.getUserPassword(), updateUser.getUserPassword());
    }

    @Test
    void 이름_변경() {
        updateUser = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("마르", "lee"));
        user.updateName(user, updateUser.getUserName());

        assertEquals(user.getUserName(), updateUser.getUserName());
    }
}
