package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.post.domain.exception.InvalidPostException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {

    Contents testContents;

    @BeforeEach
    void setUp() {
        testContents = new Contents("hello");
    }

    @Test
    void 생성() {
        Post post = new Post(testContents);

        assertThat(post.getContents()).isEqualTo(testContents);
    }

    @Test
    void 내용이_없는_경우_예외_테스트() {
        assertThrows(InvalidPostException.class, () -> new Post(null));
    }

//    @Test
//    void 유저가_없는_경우_예외_테스트() {
//        assertThrows(InvalidPostException.class, () ->  new Post(testContents, null));
//    }
//
//    @Test
//    void 내용과_유저가_없는_경우_예외_테스트() {
//        assertThrows(InvalidPostException.class, () ->  new Post(null, null));
//    }
}
