package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
public class CommentGoodRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentGoodRepository commentGoodRepository;

    private User user;
    private Post post;
    private Comment comment;
    private CommentGood commentGood;

    @BeforeEach
    void setUp() {
        user = testEntityManager.persist(User.builder()
                .name("pkch")
                .email("pkch@woowa.com")
                .password("passw0rD!")
                .build());

        post = testEntityManager.persist(Post.builder()
                .contents(new Contents("hello world!"))
                .author(user)
                .build());

        comment = testEntityManager.persist(Comment.builder()
                .contents("안녕 세계!")
                .post(post)
                .author(user)
                .build());

        commentGood = commentGoodRepository.save(new CommentGood(user, comment));
    }

    @Test
    void 생성() {
        assertThat(commentGood.getUser()).isEqualTo(user);
        assertThat(commentGood.getComment()).isEqualTo(comment);
    }

    @Test
    void 삭제() {
        assertDoesNotThrow(() -> commentGoodRepository.delete(commentGood));
    }
}
