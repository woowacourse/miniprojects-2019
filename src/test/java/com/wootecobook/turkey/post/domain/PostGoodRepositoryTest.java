package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.good.domain.post.PostGood;
import com.wootecobook.turkey.good.domain.post.PostGoodRepository;
import com.wootecobook.turkey.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
public class PostGoodRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostGoodRepository postGoodRepository;

    private User user;
    private Post post;
    private PostGood postGood;

    @BeforeEach
    void setUp() {
        user = testEntityManager.persist(User.builder()
                .name("pkch")
                .email("pkch@woowa.com")
                .password("passw0rD!")
                .build());

        post = testEntityManager.persistAndFlush(Post.builder()
                .contents(new Contents("hello world!"))
                .author(user)
                .build());

        postGood = postGoodRepository.save(new PostGood(user, post));
    }

    @Test
    void 생성() {
        assertThat(postGood.getUser()).isEqualTo(user);
        assertThat(postGood.getPost()).isEqualTo(post);
    }

    @Test
    void 삭제() {
        assertDoesNotThrow(() -> postGoodRepository.delete(postGood));
    }
}
