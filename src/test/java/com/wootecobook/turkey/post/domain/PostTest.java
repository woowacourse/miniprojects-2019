package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import com.wootecobook.turkey.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {

    User user;
    Contents testContents;

    @BeforeEach
    void setUp() {
        user = new User();
        testContents = new Contents("hello");
    }

    @Test
    void 생성() {
        Post post = new Post(testContents, user);

        assertThat(post.getContents()).isEqualTo(testContents);
    }

    @Test
    void 내용이_없는_경우_예외_테스트() {
        assertThrows(InvalidPostException.class, () ->  new Post(null, user));
    }

    @Test
    void 유저가_없는_경우_예외_테스트() {
        assertThrows(InvalidPostException.class, () ->  new Post(testContents, null));
    }

    @Test
    void 내용과_유저가_없는_경우_예외_테스트() {
        assertThrows(InvalidPostException.class, () ->  new Post(null, null));
    }
}
