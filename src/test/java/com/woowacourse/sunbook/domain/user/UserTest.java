package com.woowacourse.sunbook.domain.user;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    private User user1;
    private User updateUser;

    @BeforeEach
    void setUp() {
        user1 = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르"));
    }

    @Test
    void 이메일_변경() {
        updateUser = new User(new UserEmail("eara12sa@naver.com"), new UserPassword("asdf1234!A"), new UserName("미르"));
        user1.updateEmail(user1, updateUser.getUserEmail());

        assertEquals(user1.getUserEmail(), updateUser.getUserEmail());
    }

    @Test
    void 비밀번호_변경() {
        updateUser = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("1q2w3e4r!A"), new UserName("미르"));
        user1.updatePassword(user1, updateUser.getPassword());

        assertEquals(user1.getPassword(), updateUser.getPassword());
    }

    @Test
    void 이름_변경() {
        updateUser = new User(new UserEmail("ddu0422@naver.com"), new UserPassword("asdf1234!A"), new UserName("마르"));
        user1.updateName(user1, updateUser.getName());

        assertEquals(user1.getName(), updateUser.getName());
    }
}
