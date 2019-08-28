package com.wootecobook.turkey.post.domain;

import com.wootecobook.turkey.friend.domain.Friend;
import com.wootecobook.turkey.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private User friend;
    private User other;

    @BeforeEach
    void setUp() {
        user = addTestUser("pkch", "pkch@woowa.com", "passw0Rd!");
        friend = addTestUser("dpudpu", "dpudpu@woowa.com", "passw0rD!");
        other = addTestUser("olaf", "olaf@woowa.com", "passw0RD!");

        testEntityManager.persist(Friend.builder()
                .relatedUserId(user.getId())
                .relatingUserId(friend.getId())
                .build());

        testEntityManager.persist(Friend.builder()
                .relatedUserId(friend.getId())
                .relatingUserId(user.getId())
                .build());
    }

    private User addTestUser(final String name, final String email, final String password) {
        return testEntityManager.persist(User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build());
    }

    @Test
    void 친구가_쓴_글_조회_테스트() {
        // given
        writePost("content1 by dpudpu", friend);
        writePost("content2 by dpudpu", friend);

        // when
        Page<Post> postByFriends = postRepository.findByUserId(PageRequest.of(0, 10), user.getId());

        // then
        assertThat(postByFriends.getTotalElements()).isEqualTo(2);
    }

    @Test
    void 친구의_글과_친구가_나에게_쓴_글_조회() {
        // given
        writePost("content1 by dpudpu", friend);
        writePost("content2 by dpudpu", friend);
        writePost("content to user by dpudpu", friend, user);

        // when
        Page<Post> postByFriends = postRepository.findByUserId(PageRequest.of(0, 10), user.getId());

        // then
        assertThat(postByFriends.getTotalElements()).isEqualTo(3);
    }

    @Test
    void 내가_친구에게_쓴_글_조회_테스트() {
        // given
        writePost("content1 by dpudpu", friend);
        writePost("content2 by user", user);
        writePost("content to dpudpu by user", user, friend);

        // when
        Page<Post> postByFriends = postRepository.findByUserId(PageRequest.of(0, 10), user.getId());

        // then
        assertThat(postByFriends.getTotalElements()).isEqualTo(3);
    }

    @Test
    void 다른_사람이_쓴_글이_조회되지_않는지_테스트() {
        // given
        writePost("content1 by olaf", other);
        writePost("content2 by olaf", other);
        writePost("content3 by pkch", user);

        // when
        Page<Post> postByFriends = postRepository.findByUserId(PageRequest.of(0, 10), user.getId());

        // then
        assertThat(postByFriends.getTotalElements()).isEqualTo(1);
    }

    private Post writePost(final String contents, final User author) {
        return writePost(contents, author, null);
    }

    private Post writePost(final String contents, final User author, final User receiver) {
        return postRepository.save(Post.builder()
                .contents(new Contents(contents))
                .author(author)
                .receiver(receiver)
                .build());
    }
}
